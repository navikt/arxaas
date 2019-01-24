package no.oslomet.aaas.model;

public class AnonymizationPayload {
    private String data;
    private MetaData metaData;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
}
/*
JSON object Sketch:
{

  "data":"CSVString......",

  "meta":{

    "sensitivityList": {"id": "quasi", "name": "quasi", "personnr": "sensitive"},

    "dataType": {

      "id": "int", "name": "string"

    },

    "hirachy": {id: "CSVstring", name: "CSVString"}

  }

}
 */
