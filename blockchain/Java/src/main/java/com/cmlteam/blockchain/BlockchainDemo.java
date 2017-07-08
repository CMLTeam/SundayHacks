package com.cmlteam.blockchain;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;

import java.lang.reflect.Field;
import java.math.BigInteger;

/**
 * @author vgorin
 *         file created on 7/8/17 4:10 PM
 */


public class BlockchainDemo {
	public static void main(String[] args) throws Exception {
		Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
		Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
		String clientVersion = web3ClientVersion.getWeb3ClientVersion();
		System.out.println("version:");
		System.out.println(clientVersion);

		EthAccounts accounts = web3.ethAccounts().send();
		System.out.println("accounts:");
		accounts.getAccounts().forEach(System.out::println);

		EthBlockNumber blockNumber = web3.ethBlockNumber().send();
		BigInteger lastBlockNumber = blockNumber.getBlockNumber();
		System.out.println("block number:");
		System.out.println(lastBlockNumber);

		EthGasPrice ethGasPrice = web3.ethGasPrice().send();
		BigInteger gasPrice = ethGasPrice.getGasPrice();
		System.out.println("gas price:");
		System.out.println(gasPrice);

		// get the next available nonce
		EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(
				"0x7b2419e0ee0bd034f7bf24874c12512acac6e21c",
				DefaultBlockParameterName.LATEST
		).send();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		System.out.println("nonce:");
		System.out.println(nonce);

		EthCompileSolidity sol = web3.ethCompileSolidity("pragma solidity ^0.4.2; contract test { function multiply(uint a) returns(uint d) {   return a * 7;   } }").send();
		String byteCode = sol.getCompiledSolidity().get("code").getCode();
		System.out.println("compiled solidity:");
		System.out.println(byteCode);

		Transaction contactTransaction = Transaction.createContractTransaction(
				"0x7b2419e0ee0bd034f7bf24874c12512acac6e21c",
				nonce,
				gasPrice,
				byteCode
		);
		EthSendTransaction transaction = web3.ethSendTransaction(contactTransaction).send();
		System.out.println("transaction (smart contract):");
		System.out.println(transaction.getTransactionHash());


		EthBlock lastBlock = web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(lastBlockNumber), true).send();
		System.out.println("last block:");
		System.out.println(toString(lastBlock.getBlock()));
	}

	public static String toString(Object result) {
		// if result is not null, print all the internal fields
		if(result != null) {
			String lineSeparator = System.getProperty("line.separator");

			StringBuilder sb = new StringBuilder();

			// use the reflection to access the fields
			Field[] fields = result.getClass().getDeclaredFields();
			for(Field f: fields) {
				f.setAccessible(true);
				sb.append(f.getName());
				sb.append(": ");
				try {
					sb.append(f.get(result));
				}
				// if field is not accessible - mention the reason
				catch(IllegalAccessException e) {
					sb.append("IllegalAccessException: ");
					sb.append(e.getMessage());
				}
				sb.append(lineSeparator);
			}

			// remove trailing line separator
			return sb.toString().substring(0, sb.length() - lineSeparator.length());
		}
		return "";
	}
}
