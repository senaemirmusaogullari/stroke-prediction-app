document.addEventListener("DOMContentLoaded", function () {
    const auth = localStorage.getItem("auth");

    if (!auth) {
        // Eğer giriş yapılmamışsa login sayfasına yönlendir
        window.location.href = "login.html";
    }
});