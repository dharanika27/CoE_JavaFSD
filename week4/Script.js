document.addEventListener("DOMContentLoaded", function () {
    const links = document.querySelectorAll(".navbar a");

    links.forEach(link => {
        link.addEventListener("click", function (event) {
            fetch(link.getAttribute("href"))
                .then(response => {
                    if (!response.ok) {
                        alert("Error: Page not found!");
                        event.preventDefault();
                    }
                })
                .catch(() => {
                    alert("Network error: Unable to reach the page.");
                    event.preventDefault();
                });
        });
    });
});
