
var tableContainer = document.getElementById("skillTable");
var skills = tableContainer.getElementsByTagName("tr");
var praises = document.getElementsByClassName("praises");
//TODO - saako jotenkin tsekattua, että skilliä ei korosteta mikäli 0 kehua
//var praises = tableContainer.querySelector(".praises");

for (var i = 0; i < skills.length; i++) {
//    if (praises[i].toString().equals("0")) {
//        continue;
//    }
    if (i <= 2) {
        skills[i].classList.add("highlight");
    } else {
        skills[i].classList.add("fourthSkill");
        break;
    }
}

