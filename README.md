## # GÖREV VE TAKIM YÖNETİM UYGULAMASI

- Proje, Intellij IDEA üzerinden, Spring Boot - Java - Maven ile oluşturulduktan sonra aşağıdaki bağımlılıklar eklenmiştir.
- Lombok
- Spring Web
- Spring Security (JWT)
- Spring Data JPA
- Validation
- PostgreSQL Driver

Bu bağımlılıklar doğrultusunda ve projenin oluşturulması için, aşağıdaki **teknolojilerin** gereksinimlerinin karşılanması hedeflenmiştir.

• Spring Boot (REST API) 

• Spring Security (JWT ile) 

• Role-Based Authorization 

• JPA (Hibernate) 

• DTO kullanımı 

• Validation 

• Global Exception Handling 

• PostgreSQL

**Proje Tanımı:** Birden fazla takımın, görevlerini yönetebildiği, kullanıcıların rollerine göre sisteme erişebildiği, görev yöentim sistemi.

**Admin**: Tüm takımları görüp, yönetebilir.

**Manager**: Kendi takımındaki kullanıcıları ve görevleri yönetebilir.

**Member**: Sadece kendisine atanan görevleri görebilir ve durumlarını değiştirebilir.

**Özellikler**: 

• Kullanıcı kayıt ve giriş sistemi (JWT token üretimi) 

• Kullanıcı rolleri (Admin, Manager, Member) 

• Takım oluşturma ve takım üyeleri ekleme 

• Görev oluşturma, listeleme, güncelleme ve silme 

• Görev detayları: başlık, açıklama, durum, öncelik, teslim tarihi 

• Her kullanıcının sadece kendi takımındaki görevleri görebilmesi 

• Görevleri duruma göre filtreleme 

• Hataların anlamlı şekilde gösterilmesi (global exception handling)

• Listeleme işlemi için gerekli yerlerde pagination yapılmıştır.
