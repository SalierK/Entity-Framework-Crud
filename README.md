# Projeyi Çalıştırma Kılavuzu

Projenin tamamı dockerize edilmiş durumdadır. Projeyi ister IntelliJ üzerinden, isterseniz sadece Docker üzerinden çalıştırabilirsiniz. Her iki yöntem için de ilgili adımlar aşağıda verilmiştir.

## Docker ile Build

1. Maven ile build alın:
   ```bash
   mvn clean install -DskipTests
   ```
   > Not: Test aşamalarını Docker üzerinde görmek istiyorsanız `-DskipTests` parametresini kaldırın.

2. Maven ile build aldığınız esnada Lombok hata mesajı alabilirsiniz. Bu durumda JDK versiyonunuzu kontrol edin:
   - Proje Oracle Open JDK 17 ile geliştirilmiştir
   - JDK 21 ve 23'te versiyon uyuşmazlıklarından kaynaklanan hatalar mevcuttur

3. Docker containerlarını ayağa kaldırın:
   - Windows için: `Docker Compose Up`
   - MAC OS için: `Docker-compose up`

4. Projenin başarıyla çalıştığını kontrol etmek için:
   - Tarayıcınızdan `localhost:8080/api/tasks` adresine erişin
   - Spring Security login sayfasını görüyorsanız proje başarıyla çalışıyor demektir

## IntelliJ ile Build

1. Sadece PostgreSQL'i ayağa kaldırın:
   ```bash
   docker-compose up postgres
   ```
   > Not: java_app image'inin down olması veya derlenmemiş olması gerekir. Kontrol etmek için `docker ps -a` komutunu kullanın.

2. IntelliJ üzerinden:
   - Projeyi açın
   - `src/main/java/com.codecraft.Crud_app/CrudAppApplication` dosyasına tıklayın
   - Üst kısımda bulunan run butonuna basın
   - Hata almanız durumunda Run/Debug Configurations sayfasından JDK versiyonunuzu kontrol edin

## Postman ile Test

### Kullanıcı Bilgileri

İki tip kullanıcı bulunmaktadır:

| Özellik  | Admin Kullanıcısı | Person Kullanıcısı |
|----------|------------------|-------------------|
| Username | admin            | person            |
| Password | adminpassword    | personpassword    |
| Role     | ADMIN            | PERSON            |
| Create   | ✅               | ❌                |
| Read     | ✅               | ✅                |
| Update   | ✅               | ❌                |
| Delete   | ✅               | ❌                |

> Not: Bu bilgilere kod içerisinde `CustomUserDetailsService` class'ından da erişebilirsiniz.

### API İşlemleri

Tüm istekler `localhost:8080/api/tasks` URL'sine yapılacaktır.

#### CREATE İşlemi
- Method: POST
- Authentication: Basic Auth
- Body (raw JSON):
  ```json
  {
      "title": "test",
      "description": "test",
      "status": 5
  }
  ```

#### DELETE İşlemi
- Method: DELETE
- URL: `localhost:8080/api/tasks/{id}`
- Authentication: Basic Auth

#### READ İşlemi
- Method: GET
- URL: `localhost:8080/api/tasks`
- Authentication: Basic Auth

#### UPDATE İşlemi
- Method: PUT
- URL: `localhost:8080/api/tasks/{id}`
- Authentication: Basic Auth
- Body: Güncellenecek alanları JSON formatında gönderin
