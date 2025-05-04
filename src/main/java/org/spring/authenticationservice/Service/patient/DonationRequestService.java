package org.spring.authenticationservice.Service.patient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.authenticationservice.DTO.donation.DonationRequestCreateDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestResponseDto;
import org.spring.authenticationservice.DTO.donation.DonationRequestUpdateDto;
import org.spring.authenticationservice.DTO.donor.RequestToGeminiDto;
import org.spring.authenticationservice.Utils.SecurityUtil;
import org.spring.authenticationservice.mapper.patient.DonationRequestMapper;
import org.spring.authenticationservice.mapper.patient.donation.DonationRequestMapperMannual;
import org.spring.authenticationservice.model.Enum.StatusEnum;
import org.spring.authenticationservice.model.donor.Donor;
import org.spring.authenticationservice.model.patient.DonationRequest;
import org.spring.authenticationservice.model.patient.Patient;
import org.spring.authenticationservice.model.security.User;
import org.spring.authenticationservice.repository.donor.DonorRepository;
import org.spring.authenticationservice.repository.patient.DonationRequestRepo;
import org.spring.authenticationservice.repository.patient.PatientRepo;
import org.spring.authenticationservice.repository.security.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationRequestService {

    private final DonationRequestRepo donationRequestRepository;
    private final PatientRepo patientRepository;
    private final DonationRequestMapper mapper;
    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;
    private final DonorRepository donorRepository;

    @Transactional
    public DonationRequestResponseDto createDonationRequest(DonationRequestCreateDto dto) {
        // Fetch patient
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        DonationRequest donationRequest = mapper.FromCreatetoDonationRequest(dto);
        donationRequest.setPatient(patient);
        // Save to DB
        DonationRequest savedRequest = donationRequestRepository.save(donationRequest);

        return mapper.toResponseDto(savedRequest);
    }

    @Transactional
    public DonationRequestResponseDto updateDonationRequest(Long requestId, DonationRequestUpdateDto dto) {
        DonationRequest existingRequest = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));
        // authorization to be handled

        if(existingRequest.getStatus() != StatusEnum.PENDING) {
            throw new RuntimeException("Only pending requests can be updated");
        }
        DonationRequest updatedRequest = mapper
                .updateDonationRequestFromDto(dto, existingRequest);

        DonationRequest savedRequest = donationRequestRepository.save(updatedRequest);

        return mapper.toResponseDto(savedRequest);
    }


    @Transactional
    public void deleteDonationRequest(Long requestId) {
        DonationRequest existingRequest = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        // logic to be added (e.g., checking ownership)
        donationRequestRepository.delete(existingRequest);
    }

    @Transactional(readOnly = true)
    public List<DonationRequestResponseDto> getPendingDonationRequests() {
        List<DonationRequest> pendingRequests = donationRequestRepository
                .findByStatus(StatusEnum.PENDING);
        return pendingRequests.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DonationRequestResponseDto> getRejectedDonationRequests() {
        List<DonationRequest> rejectedRequests = donationRequestRepository
                .findByStatus(StatusEnum.REJECTED);

        return rejectedRequests.stream()
                .map(donationRequest -> {
                    DonationRequestResponseDto dto = new DonationRequestResponseDto();
                    return DonationRequestMapperMannual.toDonationResponseDto(donationRequest, dto);
                })
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public DonationRequestResponseDto getDonationRequestById(Long requestId) {
        DonationRequest request = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found with id: " + requestId));

        DonationRequestResponseDto responseDto = new DonationRequestResponseDto();
        return DonationRequestMapperMannual.toDonationResponseDto(request, responseDto);
    }

    @Transactional(readOnly = true)
    public List<DonationRequestResponseDto> getAllDonationRequests() {
        List<DonationRequest> requests = donationRequestRepository.findAll();
        return requests.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DonationRequestResponseDto> getDonationRequestsByPatientIdAndStatus(Long patientId) {
        List<DonationRequest> requests = donationRequestRepository.findByPatient_PatientId(patientId)
                .stream()
                .filter(request -> request.getStatus() == StatusEnum.REJECTED ||
                        request.getStatus() == StatusEnum.PENDING ||
                        request.getStatus() == StatusEnum.ADMIN_APPROVED)
                .toList();

        return requests.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DonationRequestResponseDto> getPatientApprovedRequestsByPatientId(Long patientId) {
        List<DonationRequest> requests = donationRequestRepository.findByPatient_PatientId(patientId)
                .stream()
                .filter(request -> request.getStatus() == StatusEnum.PATIENT_APPROVED)
                .toList();

        return requests.stream()
                .map(request -> {
                    DonationRequestResponseDto dto = new DonationRequestResponseDto();
                    return DonationRequestMapperMannual.toDonationResponseDto(request, dto);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DonationRequestResponseDto> getApprovedDonationRequests() {
        List<DonationRequest> approvedRequests = donationRequestRepository
                .findByStatus(StatusEnum.ADMIN_APPROVED);
        return approvedRequests.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<DonationRequestResponseDto> getPatientAcceptedDonationRequests() {
        List<DonationRequest> acceptedRequests = donationRequestRepository
                .findByStatus(StatusEnum.PATIENT_APPROVED);

        User user = userRepository.findByEmail(securityUtil.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Donor donor = donorRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new RuntimeException("Donor not found")
        );

        List<RequestToGeminiDto> geminiRequestDtos = acceptedRequests.stream()
                .map(donationRequest -> RequestToGeminiDto.builder()
                        .requestId(donationRequest.getRequestId())
                        .UserDescription(donor.getDescription())
                        .patientName(donationRequest.getPatient().getFirstName() + " " + donationRequest.getPatient().getLastName())
                        .patientAddress(donationRequest.getPatient().getPermanentAddress())
                        .donationRequestTitle(donationRequest.getTitle())
                        .donationRequestMessageToPatient(donationRequest.getMessageToPatient())
                        .donationRequestDescription(donationRequest.getDescription())
                        .build())
                .toList();

        String geminiApiKey = "######";
        String geminiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-preview-04-17:generateContent?key=" + geminiApiKey;

        String instruction = """
    You are a helpful assistant working on a donation platform.

    Your task is to sort donation requests based on how emotionally relevant they are to a donor's personal story.

    Use the donor’s background description and compare it with each donation request. Look for emotional triggers such as:

    1. Similar diseases or medical conditions.
    2. Mention of names in the donor’s story that match or closely resemble patient names (e.g., "Chathurangai Silva" from the donor story and "Chathura Silva" in a request).
       - Treat partial name matches like "Chathurangai Silva" ≈ "C. Silva" as relevant.
    3. Locations the donor mentions or is emotionally connected to.
    4. Sentimental messages or themes that may resonate based on the donor’s experience.

    ⚠️ For matching names:
    - Consider abbreviations or shortened versions (e.g., "Chathurangai Silva" could emotionally connect to a request with the name "C. Silva").

    Return the list of donation requests **sorted in descending order of emotional relevance**.
    You do not need to add or change any content. Just return the sorted list.
    """;


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(geminiRequestDtos);

            String requestBody = """
        {
          "generationConfig": {
            "temperature": 1,
            "topP": 0.95,
            "topK": 64,
            "maxOutputTokens": 65536,
            "responseMimeType": "application/json"
          },
          "contents": [
            {
              "role": "user",
              "parts": [
                { "text": %s }
              ]
            }
          ],
          "systemInstruction": {
            "role": "user",
            "parts": [
              { "text": %s }
            ]
          }
        }
        """.formatted(
                    objectMapper.writeValueAsString(jsonPayload),
                    objectMapper.writeValueAsString(instruction)
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(geminiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Gemini Response Extract
            String sortedJson = extractSortedJson(response.body());

            List<RequestToGeminiDto> sortedDtos = objectMapper.readValue(sortedJson, new com.fasterxml.jackson.core.type.TypeReference<>() {});

            Map<Long, DonationRequest> requestMap = acceptedRequests.stream()
                    .collect(Collectors.toMap(DonationRequest::getRequestId, dr -> dr));

            return sortedDtos.stream()
                    .map(dto -> {
                        DonationRequest requests = requestMap.get(dto.getRequestId());
                        DonationRequestResponseDto responseDto = new DonationRequestResponseDto();
                        return DonationRequestMapperMannual.toDonationResponseDto(requests, responseDto);
                    })
                    .toList();

        } catch (Exception e) {
            System.err.println("Gemini API failed: " + e.getMessage());

            return acceptedRequests.stream()
                    .map(request -> {
                        DonationRequestResponseDto responseDto = new DonationRequestResponseDto();
                        return DonationRequestMapperMannual.toDonationResponseDto(request, responseDto);
                    })
                    .toList();
        }
    }


    // 👇 Optional: Extract only the sorted JSON array from Gemini response
    private String extractSortedJson(String geminiResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(geminiResponse);
            String textContent = root.path("candidates").get(0)
                    .path("content").path("parts").get(0).path("text").asText();
            return textContent;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract sorted JSON from Gemini response", e);
        }
    }





    @Transactional
    public DonationRequestResponseDto updateConfirmation(
            Long requestId,
            StatusEnum status,
            String messageToPatient) {
        DonationRequest request = donationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Donation request not found"));

        request.setStatus(status);
        request.setMessageToPatient(messageToPatient);
        request.setAdminApprovedAt(LocalDateTime.now());
        // TODO: Set adminId from security context
//        request.setAdminId(SecurityContextHolder.getContext().getAuthentication().getName());

        DonationRequest savedRequest = donationRequestRepository.save(request);
        return mapper.toResponseDto(savedRequest);
    }

    public List<DonationRequestResponseDto> getAllDonationPatientRequests() {
        //need to get the donation request where status is PATIENT_APPROVED
        List<DonationRequest> donationRequests = donationRequestRepository
                .findByStatus(StatusEnum.PENDING);
        return donationRequests.stream()
                .map(donationRequest -> {
                    DonationRequestResponseDto dto = new DonationRequestResponseDto();
                    return DonationRequestMapperMannual.toDonationResponseDto(donationRequest, dto);
                })
                .collect(Collectors.toList());
    }

    public List<DonationRequestResponseDto> getAllDonationPatientRequestsNotAccepted() {
        //need to get the donation request where status is PATIENT_APPROVED
        List<DonationRequest> donationRequests = donationRequestRepository
                .findByStatus(StatusEnum.ADMIN_APPROVED);
        return donationRequests.stream()
                .map(donationRequest -> {
                    DonationRequestResponseDto dto = new DonationRequestResponseDto();
                    return DonationRequestMapperMannual.toDonationResponseDto(donationRequest, dto);
                })
                .collect(Collectors.toList());
    }

}
