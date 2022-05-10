const { getRandomInt, sendTraffic } = require('./helper');
const { isWeekend } = require('date-fns');

const trigger = async () => {
  let base = 3 + getRandomInt(2);
  const now = new Date();
  if (isWeekend(now)) {
    // Peak time => assume can multiply 2 time
    base *= getRandomInt(2);
  }
  await sendTraffic(base);
  console.log(`Send Traffice success! at ${now}`);
};

(async () => {
  trigger();
})();
