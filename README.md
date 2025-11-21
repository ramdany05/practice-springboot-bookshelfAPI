
## 🎯 Goal Latihan

Membangun **Bookshelf API** dengan **Spring Boot** yang:

* Meniru **perilaku** dan **response format** dari spesifikasi di atas.
* Menggunakan **layered architecture**: `controller → service → repository (in-memory dulu) → model/DTO`.
* Fokus ke **REST API**, **validasi**, dan **error handling**.

---

## 🔹 Stage 1 – Setup Project & Port

**Task 1 – Buat project Spring Boot**

* Gunakan:

    * Java 17+
    * Spring Boot 3+
* Tambah dependencies:

    * `spring-boot-starter-web`
    * `spring-boot-starter-validation`
    * `lombok`
    * (optional nanti: `spring-boot-starter-data-jpa`, `h2` / `mysql`)

**Task 2 – Ubah port ke 9000**

* Di `src/main/resources/application.properties`:

  ```properties
  server.port=9000
  ```
* Jalankan pakai:

    * `./mvnw spring-boot:run` **atau**
    * Run main class dari IDE.

> 🎯 Target: Aplikasi Spring Boot bisa diakses di `http://localhost:9000`.

---

## 🔹 Stage 2 – Data Model & DTO

**Task 3 – Buat model `Book`**

Buat class `Book` di package `model` atau `domain` dengan field:

```java
private String id;
private String name;
private int year;
private String author;
private String summary;
private String publisher;
private int pageCount;
private int readPage;
private boolean finished;
private boolean reading;
private String insertedAt;
private String updatedAt;
```

* `finished` = `pageCount == readPage`
* `insertedAt` & `updatedAt` = `Instant.now().toString()` atau `OffsetDateTime` → `.toString()`.

**Task 4 – Buat DTO untuk request & response**

* `BookRequest` (untuk POST & PUT body):

  ```java
  private String name;
  private Integer year;
  private String author;
  private String summary;
  private String publisher;
  private Integer pageCount;
  private Integer readPage;
  private Boolean reading;
  ```

  Tambahkan anotasi validasi:

    * `@NotBlank(message = "...")` untuk `name`
    * `@NotNull` untuk field yang wajib.

* `BookListItemResponse` (untuk GET /books → list):

  ```java
  private String id;
  private String name;
  private String publisher;
  ```

* `BookDetailResponse` (untuk GET /books/{id}`):
  Semua field `Book`.

---

## 🔹 Stage 3 – Repository (In-Memory)

**Task 5 – Buat repository in-memory**

Untuk latihan awal, jangan pakai database dulu.

* Buat interface:

  ```java
  public interface BookRepository {
      Book save(Book book);
      Optional<Book> findById(String id);
      List<Book> findAll();
      void deleteById(String id);
      boolean existsById(String id);
  }
  ```

* Buat implementasinya pakai `List<Book>`:

  ```java
  @Repository
  public class InMemoryBookRepository implements BookRepository {
      private final List<Book> books = new ArrayList<>();
      // implementasi method di atas
  }
  ```

---

## 🔹 Stage 4 – Service Layer (Business Logic)

**Task 6 – Buat `BookService`**

Di service ini kamu implementasikan **semua aturan bisnis** dari kriteria:

* `addBook(BookRequest request)` → return `String bookId`

    * Validasi:

        * Jika `name` kosong → lempar custom exception `BadRequestException` dengan message:

            * `"Gagal menambahkan buku. Mohon isi nama buku"`
        * Jika `readPage > pageCount` → lempar `BadRequestException` dengan message:

            * `"Gagal menambahkan buku. readPage tidak boleh lebih besar dari pageCount"`
    * Generate `id` (pakai `UUID.randomUUID().toString()` sebagai pengganti nanoid).
    * Set `finished`, `insertedAt`, `updatedAt`.
    * Simpan ke repository.

* `getAllBooks()` → return `List<BookListItemResponse>`

* `getBookDetail(String id)` → return `BookDetailResponse`

    * Jika tidak ditemukan → lempar `NotFoundException` dengan message:

        * `"Buku tidak ditemukan"`

* `updateBook(String id, BookRequest request)`

    * Jika `name` null/blank → `"Gagal memperbarui buku. Mohon isi nama buku"`
    * Jika `readPage > pageCount` → `"Gagal memperbarui buku. readPage tidak boleh lebih besar dari pageCount"`
    * Jika id tidak ada → `"Gagal memperbarui buku. Id tidak ditemukan"`

* `deleteBook(String id)`

    * Jika id tidak ditemukan → `"Buku gagal dihapus. Id tidak ditemukan"`

---

## 🔹 Stage 5 – REST Controller & Response Format

**Task 7 – Buat `BookController`**

Gunakan:

```java
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
}
```

Lalu implementasikan endpoint berikut:

### 1) POST /books

* Body: `BookRequest`
* Jika sukses:

    * **Status**: 201
    * Body:

      ```json
      {
        "status": "success",
        "message": "Buku berhasil ditambahkan",
        "data": {
          "bookId": "..."
        }
      }
      ```
* Jika gagal (name kosong atau readPage > pageCount):

    * **Status**: 400
    * Body sesuai spesifikasi.

### 2) GET /books

* **Status**: 200
* Body:

  ```json
  {
    "status": "success",
    "data": {
      "books": [
        {
          "id": "Qbax5Oy7L8WKf74l",
          "name": "Buku A",
          "publisher": "Dicoding Indonesia"
        }
      ]
    }
  }
  ```
* Jika kosong:

  ```json
  {
    "status": "success",
    "data": {
      "books": []
    }
  }
  ```

### 3) GET /books/{bookId}

* Jika ditemukan → 200 + objek lengkap.
* Jika tidak ditemukan:

    * **Status**: 404
    * Body:

      ```json
      {
        "status": "fail",
        "message": "Buku tidak ditemukan"
      }
      ```

### 4) PUT /books/{bookId}

* Body: `BookRequest`
* Jika sukses:

    * **Status**: 200
    * Body:

      ```json
      {
        "status": "success",
        "message": "Buku berhasil diperbarui"
      }
      ```
* Kalau `name` kosong → 400 + message sesuai spek.
* Kalau `readPage > pageCount` → 400 + message sesuai spek.
* Kalau id tidak ada → 404 + message sesuai spek.

### 5) DELETE /books/{bookId}

* Jika id ditemukan:

    * **Status**: 200
    * Body:

      ```json
      {
        "status": "success",
        "message": "Buku berhasil dihapus"
      }
      ```
* Jika id tidak ditemukan:

    * **Status**: 404
    * Body:

      ```json
      {
        "status": "fail",
        "message": "Buku gagal dihapus. Id tidak ditemukan"
      }
      ```

---

## 🔹 Stage 6 – Global Error Handling (Bonus tapi penting)

**Task 8 – Tambah `@ControllerAdvice`**

* Buat class `GlobalExceptionHandler` yang:

    * Tangani `BadRequestException` → balas 400 dengan format:

      ```json
      {
        "status": "fail",
        "message": "..."
      }
      ```
    * Tangani `NotFoundException` → balas 404 dengan format sama.
    * (Opsional) Tangani `MethodArgumentNotValidException` dari Spring Validation.

---

## 🔹 Stage 7 – Level Lanjut (Bonus)

Kalau yang di atas sudah jalan, kamu bisa upgrade:

1. **Ganti repository in-memory → JPA + H2/MySQL**

    * Buat entity `Book` dengan `@Entity`.
    * Pakai `JpaRepository<Book, String>`.
2. Tambah **filtering** di GET `/books` (bonus banget):

    * Query param `name`, `reading`, `finished`.

---

Kalau kamu mau, kirim:

* struktur folder yang kamu rencanakan,
* atau class yang sudah kamu buat,

nanti aku bantu review dan kasih *feedback* layaknya senior backend di timmu 💪
