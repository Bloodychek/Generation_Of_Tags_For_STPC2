const birkaForms = document.querySelectorAll(".form__birka");
birkaForms.forEach(form => {
    form.addEventListener('submit',  (e) => {
        e.preventDefault();
        form.submit();
        form.reset();
    });
});

const masterForms = document.querySelectorAll(".form__master");

masterForms.forEach(form => {
    form.addEventListener('submit',  async (e) => {
        e.preventDefault();
        if (checkMachineCheckboxes(form) === false) {
            alert('Вы не выбрали не 1 машины');
            return;
        };
        const area = form.elements.area.value;
        let response = await fetch('main', {
            method: 'PATCH',
            body: new FormData(form)
        });
        fetch(`api/masterTable/${area}`, {
            method: "GET",
            headers : {
                "Content-Type": "application/json; charset=UTF-8",
            },
        })
            .then((res) => res.json())
            .then(async (data) => {
                console.log(data);
                const {createTableData} = await import("/js/masterTableFilling.js");
                createTableData(data);
            }).then(
                form.reset()
        ).catch(error => alert(error.message));
    });
});

// валидация checkboxes
function checkMachineCheckboxes(form) {
    const checkboxes = form.querySelectorAll('input[type=checkbox]');
    const checkboxesArr = Array.from(checkboxes);
    return checkboxesArr.some((el) => {
        if (el.checked) return true;
    });
}