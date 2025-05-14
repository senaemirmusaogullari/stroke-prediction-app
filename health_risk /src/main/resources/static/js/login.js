document.addEventListener("DOMContentLoaded", function () {
  const loginForm = document.getElementById("loginForm");

  loginForm.addEventListener("submit", async function (e) {
    e.preventDefault();

    // ✅ Bootstrap doğrulama: geçerli değilse engelle
    if (!loginForm.checkValidity()) {
      loginForm.classList.add("was-validated");
      return;
    }

    const username = document.getElementById("loginUsername").value.trim();
    const password = document.getElementById("loginPassword").value.trim();

    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      const data = await response.json();

      if (response.ok) {
        localStorage.setItem("token", data.token);
        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("username", username);

        showAlert("Başarıyla giriş yapıldı!", "success");
        setTimeout(() => {
          window.location.href = "index.html";
        }, 1500);
      } else {
        showAlert(data.error || "Giriş başarısız!", "danger");
      }
    } catch (error) {
      console.error(error);
      showAlert("Sunucuya bağlanılamadı!", "danger");
    }
  });
});