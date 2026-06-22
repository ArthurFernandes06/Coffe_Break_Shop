document.addEventListener("DOMContentLoaded", () => {
    const sectionMain = document.getElementById("section_main");

    function ajustarLayout() {
        if (!sectionMain) {
            return;
        }

        sectionMain.style.display = window.innerWidth < 760 ? "grid" : "flex";
    }

    ajustarLayout();
    window.addEventListener("resize", ajustarLayout);
});
