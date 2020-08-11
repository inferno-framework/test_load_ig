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
        System.out.print("loading: allocated memory " + getUsedMemoryAsMbs() + " MB, ");
        System.out.print("free memory " + getFreeMemoryAsMbs() + " MB, ");
        System.out.println("max memory " + getTotalMemoryAsMbs() + " MB");

        // The method under test:
        hl7Validator.loadIg(id + (version != null ? "#" + version : ""), true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static String getFreeMemoryAsMbs() {
    long MemoryFreeSize = Runtime.getRuntime().freeMemory();
    double MemoryFreeSizeInMb = (MemoryFreeSize / 1024.0 / 1024.0);
    return NumberFormat.getIntegerInstance().format(MemoryFreeSizeInMb);
  }

  public static String getUsedMemoryAsMbs() {
    long MemoryUsedSize = Runtime.getRuntime().totalMemory();
    double MemoryUsedSizeInMb = (MemoryUsedSize / 1024.0 / 1024.0);
    return NumberFormat.getIntegerInstance().format(MemoryUsedSizeInMb);
  }
  
  public static String getTotalMemoryAsMbs() {
    long MemoryTotalSize = Runtime.getRuntime().maxMemory();
    double MemoryTotalSizeInMb = (MemoryTotalSize / 1024.0 / 1024.0);
    return NumberFormat.getIntegerInstance().format(MemoryTotalSizeInMb);
  }
}
