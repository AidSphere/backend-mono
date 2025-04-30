# 🩺 AidSphere – A Donation Platform for Cancer Patients

**AidSphere** is a secure and intelligent donation platform designed to help cancer patients receive medical support. It connects patients, donors, and medicine suppliers with transparency, AI-powered verification, and fraud prevention mechanisms.

---

## 🚀 Features

### 👨‍⚕️ Patient Module
- Register and submit medical case requests
- Upload personal and medical documents (PDF, image)
- AI-assisted document analysis for verification
- Manual operator approval

### 💰 Donor Module
- View verified patient profiles
- Donate money or medicines
- Transparent donation history and impact tracking

### 💊 Supplier (Importer) Module
- Bid to supply requested medicines
- Transparent bidding process

### 🔐 Operator Dashboard
- Approve/Reject patient submissions
- Review AI-analyzed document summaries
- Flag potential fraud or inconsistency

### 🤖 AI-Based Systems
- OCR + NLP for document extraction and summary
- Fraud detection in donation behavior and transaction history
- Social media scraping for additional patient data validation (optional module)

---

## 🧠 Tech Stack

### 🔧 Backend
- **Spring Boot (Java)** – Core REST APIs
- **PostgreSQL** – Structured data
- **Redis** – Caching & session management

### 🌐 Frontend
- **Next.js (App Router)** – Modern frontend with SSR & CSR
- **TailwindCSS** – Utility-first styling
- **React Query (TanStack)** – Data fetching and cache management

---

## 🛡️ Security & Authentication

- OAuth2 login (Google)
- Role-based access (patient, donor, operator, supplier)
- OTP/email verification for critical actions
- Secure API access and token refresh

---

## 📦 Project Structure

./aidsphere-backend -> Spring Boot REST APIs  
./aidsphere-frontend -> Next.js frontend

---

## 📌 Roadmap

### ✅ Core Platform (MVP)
- [x] Patient registration and donation request submission  
- [x] Donor portal for viewing and supporting patient cases  
- [x] Importer portal for medicine bidding and supply  
- [x] Admin portal for user, donations and content management  
- [x] Manual verification workflow for patient approval  
- [x] Secure donation flow with integrated payment processing  

### 🔄 Platform Enhancements
- [ ] Fraud detection system for donors and patients  
- [ ] AI-powered document extraction and summarization (OCR + NLP) (in progress)

### 📱 Expansion & Intelligence
- [ ] Mobile app (React Native) for broader access  
- [ ] AI chatbot assistant for guiding donors and patients (RAG-based)  
- [ ] Real-time notifications via WhatsApp/email/SMS  

### 📊 Analytics & Transparency
- [ ] Donation impact visualization for donors  
- [x] Case progress tracking for patients  
- [ ] Operator activity and decision logs




