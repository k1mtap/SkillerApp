//var listContainer = document.getElementById("skillList");
//var skills = listContainer.getElementsByTagName("li");
//
//for (var i = 0; i < skills.length; i++) {
//    
//    skills[i].classList.add("highlight");
//    
//    if (i === 2) {
//        break;
//    }
//}

var tableContainer = document.getElementById("skillTable");
var skills = tableContainer.getElementsByTagName("tr");
//TODO - saako jotenkin tsekattua, että skilliä ei korosteta mikäli 0 kehua
//var praises = tableContainer.querySelector(".praises");

for (var i = 0; i < skills.length; i++) {
    
    skills[i].classList.add("highlight");

    if (i === 2) {
        break;
    }
}

