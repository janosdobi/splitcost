function placeUserNameIntoHeader(dto) {
  const headerElement = document.querySelector('#welcome_message');
  const { userName } = dto;
  headerElement.textContent = `Welcome ${userName}! This is your SplitCost dashboard.`;
}

function extractData(dtoData) {
  const { userBalanceMap } = dtoData;
  return userBalanceMap;
}

function createTd(userName, balance, tableRow) {
  const td = document.createElement('td');
  const msgNegBalance = `You owe ${userName} HUF ${Math.abs(balance)}. Pay up!`;
  const msgPosBalance = `${userName} owes you HUF ${balance}. Time to collect!`;
  const msgInP = document.createElement('p');
  msgInP.textContent = balance < 0 ? msgNegBalance : msgPosBalance;
  td.appendChild(msgInP);
  tableRow.appendChild(td);
}

function populateDashboard(userBalance) {
  const tableElement = document.querySelector('#zs_dashboard');

  // eslint-disable-next-line no-restricted-syntax
  for (const [userName, balance] of Object.entries(userBalance)) {
    const tableRow = document.createElement('tr');
    createTd(userName, balance, tableRow);
    tableElement.appendChild(tableRow);
  }
}

function isEmpty(obj) {
  return !Object.keys(obj).length > 0;
}

function renderNoCostMessage(userBalanceMap) {
  if (isEmpty(userBalanceMap)) {
    const p = document.createElement('p');
    p.textContent = 'You do not owe nothing, and have no exposures. Go spend some money.';
    const noCostDiv = document.querySelector('.no_cost');
    noCostDiv.appendChild(p);
  }
}

function getDataFromDTO() {
  const request = new XMLHttpRequest();

  request.open('GET', 'http://localhost:8080/zsebzsi/rest_dashboard');

  let dto;

  // eslint-disable-next-line func-names
  request.onload = function () {
    dto = JSON.parse(this.response);
    placeUserNameIntoHeader(dto);
    const userBalanceMap = extractData(dto);
    renderNoCostMessage(userBalanceMap);
    populateDashboard(userBalanceMap);
  };

  request.send();
}

getDataFromDTO();
