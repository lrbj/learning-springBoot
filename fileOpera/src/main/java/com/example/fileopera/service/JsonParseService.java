package com.example.fileopera.service;

import com.example.fileopera.dto.Function;
import com.example.fileopera.dto.Module;
import com.example.fileopera.dto.ModuleFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Kayla, Ye
 * @Description:
 * @Date:Created in 1:59 PM 1/10/2020
 */
@Service
public class JsonParseService {

    public List<Module> getModules(List<ModuleFunction> moduleFunctionList){

        if(moduleFunctionList.isEmpty()){
            return null;
        }
        List<Module> moduleList = new ArrayList<>();
        for(ModuleFunction moduleFunction: moduleFunctionList){
            Module module = new Module();
            module.setName(moduleFunction.getName());
            List<String> funcs = new ArrayList<>();
            List<Function> functionList = moduleFunction.getFunctions();
            if(null == functionList || functionList.isEmpty()){
                moduleList.add(module);
                continue;
            }

            for(Function function: functionList){
                funcs.add(function.getName());
            }
            module.setFunctions(funcs);
            moduleList.add(module);
        }
        return  moduleList;
    }
}
