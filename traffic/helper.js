const uuid = require('uuid');
const axios = require('axios').default;

const baseUrl = process.env.BASE_URL || "http://localhost:5000";

const getRandomInt = (max) => {
  return Math.floor(Math.random() * max);
};

const sendTraffic = async (time) => {
  const menus = await getProducts();
  console.log(menus);
  const listOfOrder = [];
  for (let i = 0; i < time; i++) {
    listOfOrder.push(generateOrder(menus));
  }
  console.log(`Order counter: ${listOfOrder.length}`);
  await Promise.all(
    listOfOrder.map(async (o) => {
      return orderToShop(o);
    })
  );
};

const getProducts = async () => {
  const url = `${baseUrl}/menus`;
  const { data } = await axios.get(url);
  return data;
};

const generateOrder = (menus) => {
  const userId = uuid.v4();
  // Guarantee 1 order
  const first = randomPickMenu(menus);
  const remaining = menus
    .filter((m) => m.id !== first.menuId)
    .map((m) => generateMenuAmount(m));
  return {
    userId,
    menus: [first, ...remaining],
  };
};

const randomPickMenu = (menus) => {
  const menu = menus[getRandomInt(menus.length)];
  return generateMenuAmount(menu);
};

const generateMenuAmount = (menu) => {
  const amount = getRandomInt(3) + 1;
  return {
    menuId: menu.id,
    amount,
  };
};

const orderToShop = async (orderDetail) => {
  const url = `${baseUrl}/orders`;
  const body = orderDetail;
  console.log(`Send order with user: ${orderDetail.userId}`);
  await axios.post(url, body);
};

module.exports = {
  getRandomInt,
  sendTraffic,
};
