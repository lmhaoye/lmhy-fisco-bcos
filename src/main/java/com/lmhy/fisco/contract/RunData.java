package com.lmhy.fisco.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.bcos.channel.client.TransactionSucCallback;
import org.bcos.web3j.abi.EventEncoder;
import org.bcos.web3j.abi.EventValues;
import org.bcos.web3j.abi.TypeReference;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Event;
import org.bcos.web3j.abi.datatypes.Function;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.bcos.web3j.protocol.core.methods.request.EthFilter;
import org.bcos.web3j.protocol.core.methods.response.Log;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.tx.Contract;
import org.bcos.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.bcos.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class RunData extends Contract {
    private static final String BINARY = "60606040526040805190810160405280600d81526020017f48656c6c6f2052756e44617461000000000000000000000000000000000000008152506000908051906020019061004f929190610060565b50341561005b57600080fd5b610105565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100a157805160ff19168380011785556100cf565b828001600101855582156100cf579182015b828111156100ce5782518255916020019190600101906100b3565b5b5090506100dc91906100e0565b5090565b61010291905b808211156100fe5760008160009055506001016100e6565b5090565b90565b61072d806101146000396000f30060606040523615610076576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063025799f01461007b578063428ebb4a14610117578063949d225d14610174578063b8dda9c71461019d578063bc8533bd14610239578063cdd1633c146102c7575b600080fd5b341561008657600080fd5b61009c6004808035906020019091905050610324565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100dc5780820151818401526020810190506100c1565b50505050905090810190601f1680156101095780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561012257600080fd5b610172600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506103df565b005b341561017f57600080fd5b6101876103f9565b6040518082815260200191505060405180910390f35b34156101a857600080fd5b6101be60048080359060200190919050506103ff565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101fe5780820151818401526020810190506101e3565b50505050905090810190601f16801561022b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561024457600080fd5b61024c6104af565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561028c578082015181840152602081019050610271565b50505050905090810190601f1680156102b95780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34156102d257600080fd5b610322600480803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091905050610557565b005b61032c610648565b600260008381526020019081526020016000208054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103d35780601f106103a8576101008083540402835291602001916103d3565b820191906000526020600020905b8154815290600101906020018083116103b657829003601f168201915b50505050509050919050565b80600090805190602001906103f592919061065c565b5050565b60015481565b60026020528060005260406000206000915090508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104a75780601f1061047c576101008083540402835291602001916104a7565b820191906000526020600020905b81548152906001019060200180831161048a57829003601f168201915b505050505081565b6104b7610648565b60008054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561054d5780601f106105225761010080835404028352916020019161054d565b820191906000526020600020905b81548152906001019060200180831161053057829003601f168201915b5050505050905090565b806002600060016000815480929190600101919050558152602001908152602001600020908051906020019061058e92919061065c565b506001543373ffffffffffffffffffffffffffffffffffffffff167f47ac0b4c076f70ee63e7fa70c13aaa4dc699b24b09ff589702285156b91521ec836040518080602001828103825283818151815260200191508051906020019080838360005b8381101561060b5780820151818401526020810190506105f0565b50505050905090810190601f1680156106385780820380516001836020036101000a031916815260200191505b509250505060405180910390a350565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061069d57805160ff19168380011785556106cb565b828001600101855582156106cb579182015b828111156106ca5782518255916020019190600101906106af565b5b5090506106d891906106dc565b5090565b6106fe91905b808211156106fa5760008160009055506001016106e2565b5090565b905600a165627a7a72305820f5486b9dfb1656c736ff14c0ce128515c059986d1b803fa39313a25cde1cff240029";

    private RunData(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private RunData(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static List<ClickEventResponse> getClickEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Click", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ClickEventResponse> responses = new ArrayList<ClickEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ClickEventResponse typedResponse = new ClickEventResponse();
            typedResponse._from = (Address) eventValues.getIndexedValues().get(0);
            typedResponse.id = (Uint256) eventValues.getIndexedValues().get(1);
            typedResponse.con = (Utf8String) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ClickEventResponse> clickEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Click", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ClickEventResponse>() {
            @Override
            public ClickEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ClickEventResponse typedResponse = new ClickEventResponse();
                typedResponse._from = (Address) eventValues.getIndexedValues().get(0);
                typedResponse.id = (Uint256) eventValues.getIndexedValues().get(1);
                typedResponse.con = (Utf8String) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Future<Utf8String> getMap(Uint256 key) {
        Function function = new Function("getMap", 
                Arrays.<Type>asList(key), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setSay(Utf8String _s) {
        Function function = new Function("setSay", Arrays.<Type>asList(_s), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setSay(Utf8String _s, TransactionSucCallback callback) {
        Function function = new Function("setSay", Arrays.<Type>asList(_s), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public Future<Uint256> size() {
        Function function = new Function("size", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> map(Uint256 param0) {
        Function function = new Function("map", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Utf8String> getSay() {
        Function function = new Function("getSay", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setMap(Utf8String _s) {
        Function function = new Function("setMap", Arrays.<Type>asList(_s), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public void setMap(Utf8String _s, TransactionSucCallback callback) {
        Function function = new Function("setMap", Arrays.<Type>asList(_s), Collections.<TypeReference<?>>emptyList());
        executeTransactionAsync(function, callback);
    }

    public static Future<RunData> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(RunData.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<RunData> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(RunData.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static RunData load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RunData(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static RunData load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RunData(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class ClickEventResponse {
        public Address _from;

        public Uint256 id;

        public Utf8String con;
    }
}
