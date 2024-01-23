const tabLink = document.querySelectorAll('.tabLinks');
console.log(tabLink);
if (!localStorage.getItem('defaultOpen')) {
    localStorage.setItem('defaultOpen', 'D');
}else {
    tabLink.forEach(el => {
        if (el.dataset.name === localStorage.getItem('defaultOpen')) {
            el.classList.add("defaultOpen");
        }
    })
}

tabLink.forEach(el => {
    el.addEventListener('click', (ev) => {
        openTab(ev,el.dataset.name);
        localStorage.setItem('defaultOpen', el.dataset.name);
    });
});

function openTab(evt, name) {
    let i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabContent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tabLinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(name).style.display = "flex";
    evt.currentTarget.className += " active";
}


document.getElementsByClassName("defaultOpen")[0].click();