
var tableContainer = document.getElementById("skillTable");
var skills = tableContainer.getElementsByTagName("tr");
//TODO - saako jotenkin tsekattua, että skilliä ei korosteta mikäli 0 kehua

for (var i = 0; i < skills.length; i++) {
    if (i <= 2) {
        skills[i].classList.add("highlight");
    } else {
        skills[i].classList.add("fourthSkill");
        break;
    }
}

