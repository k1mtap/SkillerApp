
var tableContainer = document.getElementById("skillTable");
var skills = tableContainer.getElementsByTagName("tr");

for (var i = 0; i < skills.length; i++) {
    if (i <= 2) {
        skills[i].classList.add("highlight");
    } else {
        skills[i].classList.add("fourthSkill");
        break;
    }
}

