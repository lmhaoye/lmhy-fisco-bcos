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
public final class SUser extends Contract {
    private static final String BINARY = "606060405260008055341561001357600080fd5b6105e5806100226000396000f30060606040523615610076576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806356d88e271461007b5780636d86b8a5146100a457806376f75e7f146100cd5780637ff1126a1461013057806398aa8b4a146101b5578063e08e6785146102b6575b600080fd5b341561008657600080fd5b61008e6102ef565b6040518082815260200191505060405180910390f35b34156100af57600080fd5b6100b76102f5565b6040518082815260200191505060405180910390f35b34156100d857600080fd5b6100ee6004808035906020019091905050610302565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561013b57600080fd5b6101b3600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610341565b005b34156101c057600080fd5b6101d6600480803590602001909190505061042d565b604051808681526020018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001848152602001806020018381526020018281038252848181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156102a35780601f10610278576101008083540402835291602001916102a3565b820191906000526020600020905b81548152906001019060200180831161028657829003601f168201915b5050965050505050505060405180910390f35b34156102c157600080fd5b6102ed600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610482565b005b60005481565b6000600180549050905090565b60018181548110151561031157fe5b90600052602060002090016000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60a06040519081016040528060005481526020018373ffffffffffffffffffffffffffffffffffffffff16815260200182815260200184815260200142815250600260008060008154809291906001019190505581526020019081526020016000206000820151816000015560208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020155606082015181600301908051906020019061041a9291906104e8565b5060808201518160040155905050505050565b60026020528060005260406000206000915090508060000154908060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169080600201549080600301908060040154905085565b600180548060010182816104969190610568565b9160005260206000209001600083909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061052957805160ff1916838001178555610557565b82800160010185558215610557579182015b8281111561055657825182559160200191906001019061053b565b5b5090506105649190610594565b5090565b81548183558181151161058f5781836000526020600020918201910161058e9190610594565b5b505050565b6105b691905b808211156105b257600081600090555060010161059a565b5090565b905600a165627a7a72305820f7392456c66d442ef3aa33509ba90473f6da275789610799e8707a22dd8aaa600029";

    private SUser(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private SUser(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<Uint256> len() {
        Function function = new Function("len", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> orderSize() {
        Function function = new Function("orderSize", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Address> orderList(Uint256 param0) {
        Function function = new Function("orderList", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> reg(Utf8String _name, Address _address, Uint256 _cate) {
        Function function = new Function("reg", Arrays.<Type>asList(_name, _address, _cate), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<List<Type>> accmap(Uint256 param0) {
        Function function = new Function("accmap", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> saveOrder(Address _od) {
        Function function = new Function("saveOrder", Arrays.<Type>asList(_od), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<SUser> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(SUser.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<SUser> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(SUser.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static SUser load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SUser(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SUser load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SUser(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
