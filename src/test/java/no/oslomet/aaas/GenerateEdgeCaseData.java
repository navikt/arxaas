package no.oslomet.aaas;

import no.oslomet.aaas.model.Attribute;
import no.oslomet.aaas.model.PrivacyCriterionModel;
import no.oslomet.aaas.model.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static no.oslomet.aaas.model.AttributeTypeModel.*;

public class GenerateEdgeCaseData {

    public static Request zipcodeRequestPayload_wrong_data_format() {
        List<String[]> testData = wrongFromatData();
        List<String[]> listHierarchy = zipcodeHierarchy();
        List<Attribute> testAttributes = ageGenderZipcodeAttributes(listHierarchy);
        List<PrivacyCriterionModel> privacyCriterionModels = ageGenderZipcodePrivacyModels();
        return new Request(testData, testAttributes, privacyCriterionModels);
    }

    private static List<String[]> wrongFromatData() {
        String[][] rawData = {{"age, gender, zipcode" },
                {"34, male, 81667"},
                {"35, female, 81668"},
                {"36, male, 81669"},
                {"37, female, 81670"},
                {"38, male, 81671"},
                {"39, female, 81672"},
                {"40, male, 81673"},
                {"41, female, 81674"},
                {"42, male, 81675"},
                {"43, female, 81676"},
                {"44, male, 81677"}};
        return  List.of(rawData);
    }

    private static List<String[]> zipcodeHierarchy() {
        //Defining Hierarchy for a give column name
        String[][] testHeirarchy = {
                {"81667", "8166*", "816**", "81***", "8****", "*****"}
                , {"81668", "8166*", "816**", "81***", "8****", "*****"}
                , {"81669", "8166*", "816**", "81***", "8****", "*****"}
                , {"81670", "8167*", "816**", "81***", "8****", "*****"}
                , {"81671", "8167*", "816**", "81***", "8****", "*****"}
                , {"81672", "8167*", "816**", "81***", "8****", "*****"}
                , {"81673", "8167*", "816**", "81***", "8****", "*****"}
                , {"81674", "8167*", "816**", "81***", "8****", "*****"}
                , {"81675", "8167*", "816**", "81***", "8****", "*****"}
                , {"81676", "8167*", "816**", "81***", "8****", "*****"}
                , {"81677", "8167*", "816**", "81***", "8****", "*****"}};

        return List.of(testHeirarchy);
    }

    private static List<Attribute> ageGenderZipcodeAttributes(List<String[]> listHierarchy) {
        //Defining attribute types(sensitive, identifying, quasi-identifying, insensitive, etc)
        List<Attribute> testAttributes = new ArrayList<>();
        testAttributes.add(new Attribute("age",IDENTIFYING, null));
        testAttributes.add(new Attribute("gender",SENSITIVE, null));
        testAttributes.add(new Attribute("zipcode",QUASIIDENTIFYING, listHierarchy));
        return testAttributes;
    }

    private static List<PrivacyCriterionModel> ageGenderZipcodePrivacyModels() {
        //Define K-anonymity
        List<PrivacyCriterionModel> privacyCriterionModelList = new ArrayList<>();
        Map<String,String> kMapValue = new HashMap<>();
        kMapValue.put("k","5");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.KANONYMITY, kMapValue));
        Map<String,String> lMapValue = new HashMap<>();
        lMapValue.put("l", "2");
        lMapValue.put("column_name", "gender");
        privacyCriterionModelList.add(new PrivacyCriterionModel(PrivacyCriterionModel.PrivacyModel.LDIVERSITY_DISTINCT, lMapValue));
        return privacyCriterionModelList;
    }
}
