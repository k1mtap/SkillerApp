var listContainer = document.getElementById("skillList");
var skills = listContainer.getElementsByTagName("li");

for (var i = 0; i < skills.length; i++) {
    
    skills[i].classList.add("highlight");
    
    if (i === 2) {
        break;
    }
}
   
