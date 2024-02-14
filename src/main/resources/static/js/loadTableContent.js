export function loadTableData(url, method = 'GET', body = null, headers = { 'Content-Type': 'application/json' }) {
    fetch(url, { method, body, headers })
        .then((res) => res.json())
        .then(async (data) => {
            const {createTableData} = await import("/js/masterTableFilling.js");
            createTableData(data);
        }).catch(error => alert(error.message));
}
