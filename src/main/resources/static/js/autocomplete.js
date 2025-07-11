document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById("city");
    const suggestionBox = document.createElement("ul");
    suggestionBox.id = "suggestions";
    suggestionBox.style.border = "1px solid #ccc";
    suggestionBox.style.position = "absolute";
    suggestionBox.style.background = "white";
    suggestionBox.style.zIndex = "1000";
    suggestionBox.style.listStyle = "none";
    suggestionBox.style.padding = "0";
    suggestionBox.style.margin = "0";
    suggestionBox.style.width = input.offsetWidth + "px";
    input.parentNode.appendChild(suggestionBox);

    input.addEventListener("input", async () => {
        const query = input.value.trim();
        suggestionBox.innerHTML = "";

        if (query.length < 2) return;

        const response = await fetch(`/api/geo?query=${query}`);
        const data = await response.json();

        data.forEach(loc => {
            const li = document.createElement("li");
            li.style.padding = "5px";
            li.style.cursor = "pointer";
            li.textContent = `${loc.name}, ${loc.country}${loc.state ? ' (' + loc.state + ')' : ''}`;
            li.addEventListener("click", () => {
                window.location.href = `/weather?lat=${loc.lat}&lon=${loc.lon}&name=${loc.name}&country=${loc.country}`;
            });
            suggestionBox.appendChild(li);
        });
    });

    // скрываем подсказки при клике вне
    document.addEventListener("click", e => {
        if (e.target !== input) {
            suggestionBox.innerHTML = "";
        }
    });
});