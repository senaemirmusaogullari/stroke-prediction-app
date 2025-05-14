document.addEventListener("DOMContentLoaded", function () {
  const registerForm = document.getElementById("registerForm");

  registerForm.addEventListener("submit", async function (e) {
    e.preventDefault();

    const username = document.getElementById("registerUsername").value.trim();
    const email = document.getElementById("registerEmail").value.trim();
    const password = document.getElementById("registerPassword").value.trim();

    // 🚨 Form doğrulama
    if (!username || !email || !password) {
      showAlert("Tüm alanları doldurunuz!", "danger");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, email, password }),
      });

      const data = await response.json();

      if (response.ok) {
        // ✅ Kayıt başarılıysa kullanıcı bilgilerini geçici olarak sakla
        localStorage.setItem("username", username);
        localStorage.setItem("email", email);

        showAlert("Başarıyla kayıt oldunuz, giriş yapabilirsiniz!", "success");

        setTimeout(() => {
          window.location.href = "login.html";
        }, 2000);
      } else {
        // ❌ Hata varsa kullanıcıya bildir
        showAlert(data.error || "Kayıt başarısız!", "danger");
      }

    } catch (error) {
      console.error("❌ Sunucu hatası:", error);
      showAlert("Sunucuya bağlanılamadı!", "danger");
    }
  });
});