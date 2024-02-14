export function createTableData(data) {
    const tableSelector = data[1].statusMachineGSVPK.area === 'D'?'tableD':"tableH";
    const table = document.querySelector(`.${tableSelector}`);
    const alterTable = document.createElement('div');
    alterTable.classList.add('table',`${tableSelector}`);
    let tdNumStan = document.createElement('div');
    tdNumStan.classList.add('td');
    tdNumStan.textContent = '№ Стана';
    let tdTabNum = document.createElement('div');
    tdTabNum.classList.add('td');
    tdTabNum.textContent = "Табельный №";
    alterTable.appendChild(tdNumStan);
    alterTable.appendChild(tdTabNum);

    data.forEach(data => {
        const row = document.createElement('div');
        row.classList.add('row');

        const tdNumStan = document.createElement('div');
        tdNumStan.classList.add('td');
        tdNumStan.textContent = data.statusMachineGSVPK.idMachine;
        const tdTabNum = document.createElement('div');
        tdTabNum.classList.add('td');
        tdTabNum.textContent = data.tabNum;
        row.appendChild(tdNumStan);
        row.appendChild(tdTabNum);
        alterTable.appendChild(row);
    });

    table.replaceWith(alterTable);

}