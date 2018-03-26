package com.lmhy.fisco.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class LUser extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6108af8061001e6000396000f30060606040523615610076576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680633d1c40aa1461007b57806375df0057146100b457806383c8a27c146101a05780638798fd88146101c95780638df5433e1461024e578063bfe58998146102b1575b600080fd5b341561008657600080fd5b6100b2600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506103a4565b005b34156100bf57600080fd5b6100eb600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061040a565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b83811015610163578082015181840152602081019050610148565b50505050905090810190601f1680156101905780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34156101ab57600080fd5b6101b36105f3565b6040518082815260200191505060405180910390f35b34156101d457600080fd5b61024c600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919080359060200190919050506105f9565b005b341561025957600080fd5b61026f60048080359060200190919050506106c4565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156102bc57600080fd5b6102d26004808035906020019091905050610703565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001806020018381526020018281038252848181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156103935780601f1061036857610100808354040283529160200191610393565b820191906000526020600020905b81548152906001019060200180831161037657829003601f168201915b505094505050505060405180910390f35b600080548060010182816103b8919061074c565b9160005260206000209001600083909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b6000610414610778565b600061041e61078c565b60008090505b6001548160ff1610156105d2578573ffffffffffffffffffffffffffffffffffffffff16600260008360ff16815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156105c557600260008260ff168152602001908152602001600020606060405190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105af5780601f10610584576101008083540402835291602001916105af565b820191906000526020600020905b81548152906001019060200180831161059257829003601f168201915b5050505050815260200160028201548152505091505b8080600101915050610424565b81600001518260200151836040015181915094509450945050509193909250565b60015481565b6060604051908101604052808473ffffffffffffffffffffffffffffffffffffffff16815260200183815260200182815250600260006001600081548092919060010191905055815260200190815260200160002060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010190805190602001906106b19291906107ca565b5060408201518160020155905050505050565b6000818154811015156106d357fe5b90600052602060002090016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60026020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169080600101908060020154905083565b81548183558181151161077357818360005260206000209182019101610772919061084a565b5b505050565b602060405190810160405280600081525090565b606060405190810160405280600073ffffffffffffffffffffffffffffffffffffffff1681526020016107bd61086f565b8152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061080b57805160ff1916838001178555610839565b82800160010185558215610839579182015b8281111561083857825182559160200191906001019061081d565b5b509050610846919061084a565b5090565b61086c91905b80821115610868576000816000905550600101610850565b5090565b90565b6020604051908101604052806000815250905600a165627a7a7230582038a036dba585a5bf36272c3b8b0ce6e9a21016b990ed2ee050fcc359c88b9e190029";

    private LUser(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private LUser(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> addOrder(Address adr) {
        Function function = new Function("addOrder", Arrays.<Type>asList(adr), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<List<Type>> getByAdr(Address adr) {
        Function function = new Function("getByAdr", 
                Arrays.<Type>asList(adr), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<Uint256> umapSize() {
        Function function = new Function("umapSize", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> add(Address _adr, Utf8String _name, Uint256 _ty) {
        Function function = new Function("add", Arrays.<Type>asList(_adr, _name, _ty), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> los(Uint256 param0) {
        Function function = new Function("los", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<List<Type>> umap(Uint256 param0) {
        Function function = new Function("umap", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public static Future<LUser> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(LUser.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<LUser> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(LUser.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static LUser load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new LUser(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static LUser load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new LUser(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
