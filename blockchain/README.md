Prerequisites:

1. Install TestRPC - Ethereum network simulator:
https://github.com/ethereumjs/testrpc

2. Install Truffle – Solidity compiler, deployer, etc.:
https://github.com/trufflesuite/truffle
http://truffleframework.com/

3. Install MetaMask - Ethereum Chrome plugin
https://metamask.io/
https://gist.github.com/flyswatter/aea93752fb90322bbe11


How to create and deploy example MetaCoin smart contract:
1. Run TestRPC:
testrpc
2. Create, compile and deploy MetaCoin example
mkdir MetaCoin
cd MetaCoin
truffle init
truffle compile
truffle deploy
truffle serve
3. Connect MetaMask
Use this tutorial: https://gist.github.com/flyswatter/aea93752fb90322bbe11


to make a function call with Truffle:
FixedSupplyToken.deployed().then(m=>m.balanceOf.call('0x7b2419e0ee0bd034f7bf24874c12512acac6e21c').then(console.log))
FixedSupplyToken.deployed().then(m=>m.transfer.call('0x9c7bc14e8a4b054e98c6db99b9f1ea2797baee7b', 1).then(console.log))

to make a transaction with Truffle:
FixedSupplyToken.deployed().then(m=>m.transfer('0x9c7bc14e8a4b054e98c6db99b9f1ea2797baee7b', 1).then(console.log))
