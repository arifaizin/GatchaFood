# GatchaFood

GatchaFood adalah aplikasi Android yang dirancang untuk memberikan rekomendasi restoran menggunakan teknologi kecerdasan buatan generatif. Aplikasi ini menggunakan model Gemini Pro untuk menyarankan tempat makan berdasarkan preferensi pengguna, yang kemudian ditampilkan dalam daftar yang mudah dinavigasi.

## Fitur Utama

* **Rekomendasi Restoran Berbasis AI:** Menggunakan model Gemini Pro dari Google untuk menghasilkan daftar 10 rekomendasi restoran dalam format JSON.
* **Antarmuka Pengguna Sederhana:** Pengguna dapat memasukkan jenis makanan (`Mau makan apa?`) dan lokasi (`Bandung`) untuk mendapatkan rekomendasi.
* **Tampilan Daftar Restoran:** Menampilkan rekomendasi restoran dalam daftar yang berisi nama, alamat, rating, jumlah ulasan, dan kisaran harga.
* **Fitur "Gatcha":** Tombol "Pilihin aku dong!" akan secara acak memilih salah satu restoran dari daftar yang tersedia.

## Teknologi

* **Bahasa Pemrograman:** Kotlin
* **Kerangka Kerja:** Android dengan Jetpack Compose
* **Model AI Generatif:** Google Gemini Pro
* **Parsing JSON:** Gson
* **Sistem Build:** Gradle (versi 8.6)

## Prasyarat

* Android Studio
* Android SDK dengan `compileSdk` versi 34 dan `minSdk` versi 34.
* Google Gemini Pro API key yang dikonfigurasi dalam `BuildConfig.apiKey`.

## Contoh Penggunaan

1.  Buka aplikasi.
2.  Di kolom teks "Mau makan apa?", masukkan jenis makanan yang Anda inginkan (misalnya, "Sunda").
3.  Tekan tombol "Go" untuk mendapatkan rekomendasi restoran.
4.  Atau, tekan tombol "Pilihin aku dong!" untuk mendapatkan rekomendasi acak.
5.  Ketuk kartu restoran untuk melihat detail tambahan.

## Kontribusi

Kami menyambut kontribusi dari komunitas! Silakan ajukan _pull request_ atau laporkan _issue_ jika Anda menemukan bug atau memiliki ide fitur baru.

## Lisensi

Proyek ini dilisensikan di bawah Lisensi MIT.

MIT License

Copyright (c) 2024 arifaizin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
