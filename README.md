# GatchaFood

GatchaFood adalah aplikasi Android yang memberikan rekomendasi restoran dengan antarmuka yang sederhana dan interaktif. Aplikasi ini dibangun dengan Jetpack Compose dan menggunakan model generatif Gemini Pro untuk menghasilkan rekomendasi berdasarkan permintaan pengguna.

## Fitur Utama

* **Rekomendasi Restoran Berbasis AI**: Pengguna dapat memasukkan jenis makanan atau tempat yang mereka inginkan, dan aplikasi akan memberikan daftar rekomendasi restoran.
* **Pilih Restoran Acak**: Jika pengguna tidak yakin ingin makan apa, mereka dapat menekan tombol "Pilihin aku dong!" untuk mendapatkan rekomendasi restoran secara acak dari daftar.
* **Informasi Detail Restoran**: Setiap rekomendasi restoran menyertakan judul, alamat, jumlah ulasan, rating, rentang harga, dan ringkasan singkat.
* **Integrasi Google Maps**: Pengguna dapat langsung membuka lokasi restoran di Google Maps dengan menekan tombol "Meluncur ke lokasi".

## Teknologi yang Digunakan

* **Kotlin**: Bahasa pemrograman utama yang digunakan untuk pengembangan aplikasi.
* **Jetpack Compose**: Kerangka kerja UI deklaratif modern untuk membangun antarmuka pengguna.
* **Material 3**: Sistem desain modern untuk komponen UI.
* **Google Gemini API**: Menggunakan model `gemini-pro` dari Google AI Generative untuk menghasilkan rekomendasi restoran.
* **Gson**: Pustaka Java untuk mengonversi objek Java menjadi representasi JSON dan sebaliknya.
* **Gradle**: Sistem build yang digunakan untuk mengelola dependensi dan proses build.

## Prasyarat

* Android Studio
* Java Development Kit (JDK) 8 atau yang lebih baru
* Kunci API (API Key) untuk Google Gemini Pro. Anda perlu membuat file `local.properties` atau `gradle.properties` untuk menyimpan kunci API Anda dengan aman.

## Instalasi dan Penggunaan

1.  **Clone repositori ini**:
    ```sh
    git clone [https://github.com/arifaizin/gatchafood.git](https://github.com/arifaizin/gatchafood.git)
    cd gatchafood
    ```

2.  **Tambahkan Google Gemini API Key Anda**:
    Buat file `gradle.properties` dan tambahkan baris berikut:
    ```properties
    apiKey="YOUR_API_KEY"
    ```

3.  **Jalankan aplikasi**:
    Buka proyek di Android Studio dan jalankan di emulator atau perangkat fisik Anda. Atau, gunakan Gradle Wrapper dari terminal:
    * Di macOS/Linux:
        ```sh
        ./gradlew assembleDebug
        ```
    * Di Windows:
        ```sh
        .\gradlew.bat assembleDebug
        ```

4.  **Contoh Penggunaan**:
    * Buka aplikasi.
    * Di bidang teks, masukkan permintaan seperti "restoran sunda" atau "sate".
    * Tekan tombol **Go**.
    * Aplikasi akan menampilkan daftar restoran yang direkomendasikan.

## Berkontribusi

Kontribusi dipersilakan! Jika Anda menemukan bug, memiliki saran fitur, atau ingin berkontribusi, silakan ajukan _issue_ atau _pull request_.

## Lisensi

Proyek ini dilisensikan di bawah Lisensi MIT. Anda bebas untuk menggunakan, memodifikasi, dan mendistribusikan kode ini. Lihat file [LICENSE](https://opensource.org/licenses/MIT) untuk detailnya.

## Kontak

Untuk pertanyaan atau saran, hubungi saya di [faizin.arief@gmail.com](mailto:faizin.arief@gmail.com).
