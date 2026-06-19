function togglePw(inputId, btn) {
    const input = document.getElementById(inputId);
    if (input.type === 'password') {
        input.type = 'text';
        btn.style.opacity = '1';
        btn.textContent = '🙈'; // Muda o ícone se quiser
    } else {
        input.type = 'password';
        btn.style.opacity = '0.5';
        btn.textContent = '👁';
    }
}