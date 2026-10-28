# Forum Hacker Umsida (Tugas Praktikum Pertemuan 6)

## 📌 Deskripsi Project
Aplikasi "Forum Hacker Umsida" adalah modifikasi dari form registrasi Jetpack Compose dengan menerapkan tema *Cybersecurity/Dark Mode*. Project ini dibuat untuk memenuhi tugas Pertemuan 6 terkait **State, Event, dan Form Validation**.

## ✨ Fitur Tambahan & Modifikasi
- **Tema Kustom:** Menggunakan `darkColorScheme` permanen dengan aksen *Neon Green*.
- **2 Field Baru:** Penambahan input **NIM Mahasiswa** dan **Nomor Telepon**.
- **Keyboard Type:** Otomatis menampilkan keyboard angka (Number/Phone) pada field khusus.

## 🛠️ Penjelasan State & Validasi
- **State Management:** Menggunakan `rememberSaveable` untuk menyimpan input teks pengguna (Nama, NIM, Telepon, Email) agar data tidak hilang saat rotasi layar (*configuration change*).
- **Event Handling:** Setiap kali pengguna mengetik di form, event `onValueChange` akan dipicu untuk memperbarui state secara *real-time*.
- **Logika Validasi:** Validasi dihitung secara dinamis saat tombol "EXECUTE REGISTRATION" ditekan (`sudahSubmit = true`). Field NIM dan Telepon diwajibkan hanya berisi angka menggunakan fungsi kustom `.all { it.isDigit() }` serta minimal 10 karakter untuk telepon.

## 📸 Bukti Screenshot
| Tampilan Awal | Validasi Error | Validasi Sukses |
| :---: | :---: | :---: |
| <img src="screenshots/awal.jpg" width="250"> | <img src="screenshots/error.jpg" width="250"> | <img src="screenshots/sukses.jpg" width="250"> |