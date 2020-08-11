package org.mitre.inferno;

import java.lang.Runtime;
import java.text.NumberFormat;
import org.hl7.fhir.utilities.VersionUtilities;
import org.hl7.fhir.validation.ValidationEngine;

public class TestLoadIg {
  public static void main(String[] args) {
    String id = "hl7.fhir.r4.core";
    String version = "4.0.1";
    int DO_TIMES = 10;
    
    try {
      final String fhirSpecVersion = "4.0";
      final String definitions = VersionUtilities.packageForVersion(fhirSpecVersion) + "#"
          + VersionUtilities.getCurrentVersion(fhirSpecVersion);

      ValidationEngine hl7Validator = new ValidationEngine(definitions);
      hl7Validator.setNative(false);
      hl7Validator.setAnyExtensionsAllowed(true);
      hl7Validator.prepare();

      for (int i = 0; i < DO_TIMES; i++) {
        System.out.print("loading: allocated heap " + getUsedHeapSizeAsMbs() + " MB, ");
        System.out.print("free heap " + getFreeHeapSizeAsMbs() + " MB, ");
        System.out.println("max heap " + getTotalHeapSizeAsMbs() + " MB");
        hl7Validator.loadIg(id + (version != null ? "#" + version : ""), true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static String getFreeHeapSizeAsMbs() {
    long heapFreeSize = Runtime.getRuntime().freeMemory();
    double heapFreeSizeInMb = (heapFreeSize / 1024.0 / 1024.0);
    return NumberFormat.getInstance().format(heapFreeSizeInMb);
  }

  public static String getUsedHeapSizeAsMbs() {
    long heapUsedSize = Runtime.getRuntime().totalMemory();
    double heapUsedSizeInMb = (heapUsedSize / 1024.0 / 1024.0);
    return NumberFormat.getInstance().format(heapUsedSizeInMb);
  }
  
  public static String getTotalHeapSizeAsMbs() {
    long heapTotalSize = Runtime.getRuntime().maxMemory();
    double heapTotalSizeInMb = (heapTotalSize / 1024.0 / 1024.0);
    return NumberFormat.getInstance().format(heapTotalSizeInMb);
  }
}
