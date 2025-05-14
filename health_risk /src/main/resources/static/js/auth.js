// Kullanıcı giriş yapmış mı kontrol eden yardımcı
function isLoggedIn() {
  return !!localStorage.getItem('token');
}

// Kullanıcı adını getir
function getUsername() {
  return localStorage.getItem('username');
}

// Kullanıcı emailini getir
function getEmail() {
  return localStorage.getItem('email');
}

// Çıkış yap
function logout() {
  localStorage.clear();
  window.location.href = 'login.html';
}

// Sayfa yüklenince korunan sayfalara erişim kontrolü
document.addEventListener('DOMContentLoaded', function () {
  const protectedPages = ['index.html', 'dashboard.html', 'prediction.html', 'profile.html', 'result.html'];

  const currentPage = window.location.pathname.split('/').pop();

  if (protectedPages.includes(currentPage) && !isLoggedIn()) {
    alert('Bu sayfaya erişmek için giriş yapmalısınız.');
    window.location.href = 'login.html';
  }
});