/**
 * login_cadastro/script.js
 * ─────────────────────────────────────────────
 * Controla as abas (Login / Cadastro) e a
 * visibilidade das senhas.
 *
 * Integração com backend (sugestão):
 *   - Capture o evento 'submit' de cada form
 *   - Serialize os campos com FormData
 *   - Faça fetch() para os endpoints da API
 */

/* ─── Alternância de abas ─── */
function switchTab(tab) {
    const formLogin    = document.getElementById('form-login');
    const formCadastro = document.getElementById('form-cadastro');
    const btnLogin     = document.getElementById('tab-login');
    const btnCadastro  = document.getElementById('tab-cadastro');

    if (tab === 'login') {
        formLogin.classList.remove('hidden');
        formCadastro.classList.add('hidden');
        btnLogin.classList.add('active');
        btnCadastro.classList.remove('active');
    } else {
        formCadastro.classList.remove('hidden');
        formLogin.classList.add('hidden');
        btnCadastro.classList.add('active');
        btnLogin.classList.remove('active');
    }
}

/* ─── Mostrar / ocultar senha ─── */
function togglePw(inputId, btn) {
    const input = document.getElementById(inputId);
    if (input.type === 'password') {
        input.type = 'text';
        btn.style.opacity = '1';
    } else {
        input.type = 'password';
        btn.style.opacity = '0.5';
    }
}

/* ─── Leitura da aba via query-string (?tab=cadastro) ─── */
document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const tabParam = params.get('tab');
    if (tabParam === 'cadastro') {
        switchTab('cadastro');
    }
});



