package com.tms.common.loader;

import com.tms.common.payload.request.SubmoduleItemTableJson;
import com.tms.common.util.CustomPair;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class SubModuleJsonLoader {
    private static final String FILE_LOAD_FAILED_ERROR = "An error occurred while reading Json";
    private static final String SUB_MODULE_ITEM_MAP_FILE = "sub_module_item_map.json";
    public static Map<String, Long> SUB_MODULE_APIS = new HashMap<>();
    private static final Map<Long, CustomPair<String, Boolean>> TABLE_SMI_MAP = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(SubModuleJsonLoader.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public SubModuleJsonLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void postConstruct() {
        load();
    }

    public void load() {
        try {
            File json = new ClassPathResource(SUB_MODULE_ITEM_MAP_FILE).getFile();
            List<SubmoduleItemTableJson> itemTableList = objectMapper.readValue(json, new TypeReference<>() {
            });
            Set<String> tableSet = new HashSet<>();
            itemTableList.forEach(item -> {
                for (String uris : item.getUris()) {
                    SUB_MODULE_APIS.put(uris, item.getSmiId());
                }

                TABLE_SMI_MAP.put(item.getSmiId(), new CustomPair<>(item.getTable(), !tableSet.add(item.getTable())));
            });
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info(FILE_LOAD_FAILED_ERROR);
        }
    }

    public static Map<Long, CustomPair<String, Boolean>> getTableSmiMap() {
        return TABLE_SMI_MAP;
    }

}
