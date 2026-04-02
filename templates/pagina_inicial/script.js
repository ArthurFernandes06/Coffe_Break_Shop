document.addEventListener("DOMContentLoaded", () => {
    const section_main = document.getElementById("section_main");

    function ajuste_layout() {
        if (window.innerWidth < 760) {
            section_main.style.display = "grid";
        } else {
            section_main.style.display = "flex";
        }
    }

    ajuste_layout();
    window.addEventListener("resize", ajuste_layout);
});