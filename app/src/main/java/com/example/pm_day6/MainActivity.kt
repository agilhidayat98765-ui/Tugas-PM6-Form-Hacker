package com.example.pm_day6

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pm_day6.ui.theme.PM_day6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TAHAP 1: MENGUBAH TEMA MENJADI DARK MODE HACKER (HIJAU NEON & HITAM)
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Color(0xFF00FF00), // Hijau Neon ala Terminal
                    onPrimary = Color.Black,
                    background = Color(0xFF0D1117), // Hitam gelap untuk background
                    surface = Color(0xFF161B22), // Abu-abu sangat gelap untuk form
                    error = Color(0xFFFF3333) // Merah menyala untuk error
                )
            ) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RegistrationScreen()
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen() {
    // =========================================================================
    // TAHAP 2: MENAMBAHKAN STATE BARU (NIM & TELEPON)
    // =========================================================================
    var nama by rememberSaveable { mutableStateOf("") }
    var nim by rememberSaveable { mutableStateOf("") } // STATE BARU: NIM
    var telepon by rememberSaveable { mutableStateOf("") } // STATE BARU: Telepon
    var email by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var konfirmasiPassword by remember { mutableStateOf("") }
    var setuju by rememberSaveable { mutableStateOf(false) }
    var passwordTerlihat by rememberSaveable { mutableStateOf(false) }

    var sudahSubmit by remember { mutableStateOf(false) }
    var berhasil by remember { mutableStateOf(false) }

    // =========================================================================
    // TAHAP 3: MENAMBAHKAN LOGIKA VALIDASI BARU
    // =========================================================================
    val errorNama = sudahSubmit && nama.trim().isEmpty()
    // Aturan NIM: Tidak boleh kosong & harus 100% berisi angka (isDigit)
    val errorNim = sudahSubmit && (nim.trim().isEmpty() || !nim.all { it.isDigit() })
    // Aturan Telepon: Minimal 10 digit & harus angka semua
    val errorTelepon = sudahSubmit && (telepon.trim().length < 10 || !telepon.all { it.isDigit() })
    val errorEmail = sudahSubmit && !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
    val errorPassword = sudahSubmit && password.length < 8
    val errorKonfirmasi = sudahSubmit && konfirmasiPassword != password
    val errorPersetujuan = sudahSubmit && !setuju

    // Memasukkan NIM dan Telepon ke dalam syarat formValid
    val formValid = nama.trim().isNotEmpty() &&
            nim.trim().isNotEmpty() && nim.all { it.isDigit() } &&
            telepon.trim().length >= 10 && telepon.all { it.isDigit() } &&
            Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() &&
            password.length >= 8 && konfirmasiPassword == password && setuju

    // Warna Teks Kustom
    val textNormal = Color(0xFFC9D1D9)
    val textMuted = Color(0xFF8B949E)
    val hackerGreen = Color(0xFF00FF00)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 22.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // HEADER TEMA HACKER
        Text(">> ROOT@UMSIDA:~#", fontSize = 16.sp, color = hackerGreen, fontWeight = FontWeight.Bold)
        Text("Forum Hacker Umsida", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text("Isi kredensial identitas untuk mengakses server.", color = textMuted, fontSize = 14.sp)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // 1. INPUT NAMA / ALIAS
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it; berhasil = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nama Lengkap / Alias") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    singleLine = true,
                    isError = errorNama,
                    supportingText = { if (errorNama) Text("Nama wajib diisi") },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = textNormal, unfocusedTextColor = textNormal)
                )

                // 2. TAHAP 4: INPUT BARU -> NIM MAHASISWA
                OutlinedTextField(
                    value = nim,
                    onValueChange = { nim = it; berhasil = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("NIM Mahasiswa") },
                    leadingIcon = { Icon(Icons.Filled.Numbers, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Keyboard Angka
                    isError = errorNim,
                    supportingText = { if (errorNim) Text("NIM tidak boleh kosong dan wajib berupa angka") },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = textNormal, unfocusedTextColor = textNormal)
                )

                // 3. TAHAP 4: INPUT BARU -> NOMOR TELEPON
                OutlinedTextField(
                    value = telepon,
                    onValueChange = { telepon = it; berhasil = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Nomor Telepon/WhatsApp") },
                    leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), // Keyboard Telepon
                    isError = errorTelepon,
                    supportingText = { if (errorTelepon) Text("Nomor wajib diisi (minimal 10 angka)") },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = textNormal, unfocusedTextColor = textNormal)
                )

                // 4. INPUT EMAIL
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; berhasil = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email Enkripsi (ProtonMail/Lainnya)") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = errorEmail,
                    supportingText = { if (errorEmail) Text("Format email tidak dikenali") },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = textNormal, unfocusedTextColor = textNormal)
                )

                // 5. INPUT PASSWORD
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; berhasil = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Kata Sandi Akses") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { passwordTerlihat = !passwordTerlihat }) {
                            Icon(
                                imageVector = if (passwordTerlihat) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = if (passwordTerlihat) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    isError = errorPassword,
                    supportingText = { if (errorPassword) Text("Kata sandi terlalu lemah (minimal 8 karakter)") },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = textNormal, unfocusedTextColor = textNormal)
                )

                // 6. KONFIRMASI PASSWORD
                OutlinedTextField(
                    value = konfirmasiPassword,
                    onValueChange = { konfirmasiPassword = it; berhasil = false },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Konfirmasi Kata Sandi") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    isError = errorKonfirmasi,
                    supportingText = { if (errorKonfirmasi) Text("Kata sandi tidak cocok (Mismatch)") },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = textNormal, unfocusedTextColor = textNormal)
                )

                // 7. CHECKBOX PERSETUJUAN
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = setuju,
                        onCheckedChange = { setuju = it; berhasil = false },
                        colors = CheckboxDefaults.colors(checkedColor = hackerGreen, checkmarkColor = Color.Black)
                    )
                    Text("Saya siap menjaga kerahasiaan sistem", fontSize = 13.sp, color = textNormal)
                }
                if (errorPersetujuan) {
                    Text("Persetujuan protokol keamanan wajib dicentang!", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }

                // 8. TOMBOL SUBMIT ALA HACKER
                Button(
                    onClick = {
                        sudahSubmit = true
                        berhasil = formValid
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = hackerGreen, contentColor = Color.Black)
                ) {
                    Text(">> EXECUTE REGISTRATION", modifier = Modifier.padding(vertical = 3.dp), fontWeight = FontWeight.Bold)
                }

                // 9. PESAN SUKSES
                if (berhasil) {
                    HorizontalDivider(color = Color(0xFF237A42))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = hackerGreen)
                        Column {
                            Text("ACCESS GRANTED", color = hackerGreen, fontWeight = FontWeight.Bold)
                            Text("Selamat bergabung di jaringan, Agent ${nama.trim()}!", color = textNormal, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
        Text("SYSTEM LOG: Koneksi terenkripsi end-to-end. Pastikan input tidak mengandung injeksi script.", fontSize = 12.sp, color = textMuted)
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    PM_day6Theme {
        RegistrationScreen()
    }
}