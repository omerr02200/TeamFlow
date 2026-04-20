# 🚀 TeamFlow — Görev ve Takım Yönetim Sistemi

![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=flat-square&logo=springsecurity)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=flat-square&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36?style=flat-square&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

Birden fazla takımın görevlerini yönetebildiği, rol tabanlı yetkilendirme ile güvenli erişim sağlayan **RESTful görev yönetim API'si**.

---

## 📋 İçindekiler

- [Özellikler](#-özellikler)
- [Teknolojiler](#-teknolojiler)
- [Mimari](#-mimari)
- [Kurulum](#-kurulum)
- [API Endpointleri](#-api-endpointleri)
- [Roller ve Yetkiler](#-roller-ve-yetkiler)
- [İletişim](#-iletişim)

---

## ✨ Özellikler

- 🔐 **JWT tabanlı kimlik doğrulama** — kayıt, giriş ve token yenileme
- 👥 **Rol tabanlı yetkilendirme** — Admin, Manager, Member
- 🏢 **Takım yönetimi** — takım oluşturma ve üye ekleme/çıkarma
- ✅ **Görev yönetimi** — oluşturma, listeleme, güncelleme, silme
- 🔖 **Görev detayları** — başlık, açıklama, durum, öncelik, teslim tarihi
- 🔍 **Duruma göre filtreleme** — TODO, IN_PROGRESS, DONE
- 📄 **Pagination** — büyük veri setleri için sayfalama desteği
- ⚠️ **Global Exception Handling** — anlamlı ve tutarlı hata mesajları
- 📦 **DTO pattern** — katmanlı mimari ile temiz veri akışı

---

## 🛠 Teknolojiler

| Katman | Teknoloji |
|--------|-----------|
| Dil | Java 17 |
| Framework | Spring Boot 3.x |
| Güvenlik | Spring Security + JWT |
| Veritabanı | PostgreSQL |
| ORM | Spring Data JPA (Hibernate) |
| Doğrulama | Bean Validation (Jakarta) |
| Yardımcı | Lombok |
| Build | Maven |
| IDE | IntelliJ IDEA |

---

## 🏗 Mimari

```
src/
└── main/
    └── java/com/example/teamflow/
        ├── controller/       # REST Controller katmanı
        ├── service/          # İş mantığı katmanı
        ├── repository/       # Veritabanı erişim katmanı (JPA)
        ├── entity/           # JPA Entity sınıfları
        ├── dto/              # Request / Response DTO'ları
        ├── security/         # JWT filter, config, UserDetails
        ├── exception/        # Global exception handler
        └── enums/            # Rol, görev durumu, öncelik enum'ları
```

---

## ⚙️ Kurulum

### Gereksinimler

- Java 17+
- PostgreSQL 14+
- Maven 3.8+

### 1. Repoyu klonlayın

```bash
git clone https://github.com/omerr02200/TeamFlow.git
cd TeamFlow
```

### 2. Veritabanını oluşturun

```sql
CREATE DATABASE teamflow_db;
```

### 3. `application.properties` dosyasını düzenleyin

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/teamflow_db
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

### 4. Uygulamayı başlatın

```bash
./mvnw spring-boot:run
```

Uygulama `http://localhost:8080` adresinde çalışmaya başlayacaktır.

---

## 📡 API Endpointleri

### 🔐 Auth

| Method | Endpoint | Açıklama |
|--------|----------|----------|
| POST | `/api/auth/register` | Yeni kullanıcı kaydı |
| POST | `/api/auth/login` | Giriş ve JWT token alma |

### 👥 Kullanıcı & Takım

| Method | Endpoint | Yetki | Açıklama |
|--------|----------|-------|----------|
| GET | `/api/teams` | Admin | Tüm takımları listele |
| POST | `/api/teams` | Admin | Yeni takım oluştur |
| POST | `/api/teams/{id}/members` | Admin, Manager | Takıma üye ekle |
| DELETE | `/api/teams/{id}/members/{userId}` | Admin, Manager | Takımdan üye çıkar |

### ✅ Görev

| Method | Endpoint | Yetki | Açıklama |
|--------|----------|-------|----------|
| GET | `/api/tasks` | Manager, Admin | Takımdaki görevleri listele |
| GET | `/api/tasks?status=TODO` | Tüm roller | Duruma göre filtrele |
| GET | `/api/tasks/{id}` | Tüm roller | Görev detayı |
| POST | `/api/tasks` | Manager, Admin | Görev oluştur |
| PUT | `/api/tasks/{id}` | Manager, Admin | Görevi güncelle |
| PATCH | `/api/tasks/{id}/status` | Member | Görev durumunu güncelle |
| DELETE | `/api/tasks/{id}` | Admin | Görevi sil |

> 💡 Tüm korumalı endpointlerde `Authorization: Bearer <token>` header'ı gereklidir.

---

## 🔑 Roller ve Yetkiler

| Rol | Yetkiler |
|-----|----------|
| **ADMIN** | Tüm takımları ve görevleri yönetir, kullanıcıları görür |
| **MANAGER** | Kendi takımındaki kullanıcıları ve görevleri yönetir |
| **MEMBER** | Sadece kendisine atanan görevleri görür ve durumunu günceller |

---

## 🗂 Görev Durumları & Öncelikleri

**Durum:** `TODO` → `IN_PROGRESS` → `DONE`

**Öncelik:** `LOW` / `MEDIUM` / `HIGH`

---

## 📬 İletişim

**Ömer** — [github.com/omerr02200](https://github.com/omerr02200)

---

> Bu proje Spring Boot ekosistemini öğrenmek ve gerçek dünya senaryolarına yakın bir uygulama geliştirmek amacıyla yapılmıştır.
