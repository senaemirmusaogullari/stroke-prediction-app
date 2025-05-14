function showAlert(message, type = "success", duration = 3000) {
  const alertPlaceholder = document.getElementById('alertPlaceholder');
  if (!alertPlaceholder) return;

  const wrapper = document.createElement('div');
  wrapper.innerHTML = `
    <div class="alert alert-${type} alert-dismissible fade show" role="alert">
      ${message}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  `;

  alertPlaceholder.append(wrapper);

  setTimeout(() => {
    wrapper.remove();
  }, duration);
}