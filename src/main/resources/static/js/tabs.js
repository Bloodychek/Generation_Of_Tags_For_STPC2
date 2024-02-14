const tabLink = document.querySelectorAll('.tabLinks');
const password = 'master';
const masterSessionTime = 900000;
let timer;


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
    el.addEventListener('click', async(ev) => {
        clearTimeout(timer);
        //при клике на мастер проверяем пароль
        if (el.dataset.name === 'Master') {
            let result = prompt('Введите пароль:');
            if (result !== password) {
                alert('Вы ввели не верный пароль');
                tabLink.forEach(tab => {
                    tab.classList.remove('active');
                })
                localStorage.setItem('defaultOpen', 'D');
                openTab(localStorage.getItem('defaultOpen'));
                return;
            } else {
                // timer(masterSessionTime);
                timer =setTimeout(() => {
                    localStorage.setItem('defaultOpen', 'D');
                    document.getElementsByClassName("tabLinks")[0].click();
                    return;
                }, masterSessionTime);
                const {loadTableData} = await import("/js/loadTableContent.js");
                loadTableData(`api/masterTable/D`);
                loadTableData(`api/masterTable/H`);
            }
        }
        openTab(el.dataset.name);
        localStorage.setItem('defaultOpen', el.dataset.name);
    });
});

function openTab(name) {
    let tabContent, tabLinks;
    tabContent = document.querySelectorAll(".tabContent");
    tabLinks = document.querySelectorAll(".tabLinks");
    //прячем содержимое табов
    tabContent.forEach(tab => {
        tab.classList.add('hide');
        tab.classList.remove('show');
    });
    //отображаем нужный контент таба
    document.getElementById(name).classList.remove('hide');
    document.getElementById(name).classList.add('show');

    //удаляем классы активности с кнопок и дабавляем на нужную
    tabLinks.forEach(el => {
        el.classList.remove('active');
    })
    document.querySelector(`[data-name=${name}]`).classList.add('active');
}

document.getElementsByClassName("defaultOpen")[0].click();