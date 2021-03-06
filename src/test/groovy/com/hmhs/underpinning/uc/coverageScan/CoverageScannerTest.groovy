package com.hmhs.underpinning.uc.coverageScan

import com.urbancode.air.AirPluginTool
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Coverage Scanner Unit Test Class utilizing the spock framework
 */
@Unroll
class CoverageScannerTest extends Specification {

    /**
     * Single test case for verifying the coverage scanner results
     * @param projectId - UrbanCode Build Project ID
     * @param processId - UrbanCode Build Process ID
     * @param buildLifeId - UrbanCode Build build life ID
     * @param scanLevel - Level of scanning, report or group
     * @param percentage - level of percentage limit
     * @param percentageType - type of percentage to look at, line method, or branch
     * @param complexity - Level of complexity to set
     * @param complexityHighLow - Look at higher or lower than the complexity number set
     * @param expectedCount - Expected number of matches to be returned
     * @return Nothing
     */
    def "get coverage report"(String projectId, String processId, String buildLifeId, String scanLevel, String percentage,
                              String percentageType, String complexity,  String complexityHighLow, Integer expectedCount) {
        given:
        // This will be required to be changed to match the admin user and password in the system
        // The admin:admin are the defaults for UC Build
        CoverageScanner coverageScanner = new CoverageScanner('https://ucbuild.example.com',
                'admin', 'admin')

        when:
        // Print out some useful information to see during the test execution
        println "Getting Coverage report for project ${projectId} process ${processId} build life ${buildLifeId}"
        println "Scan Level: ${scanLevel} Percentage: ${percentage} Percentage Type: ${percentageType} Complexity: ${complexity}"

        // Run the test method
        def result = coverageScanner.getCoverageInfo(projectId, processId, buildLifeId, scanLevel, percentage,
                percentageType, complexity, complexityHighLow)

        // Show the actual result being returned
        println result

        then:
        // Verify the result received
        assert expectedCount == result.size()

        where:
        // Test data
        projectId | processId | buildLifeId | scanLevel | percentage | percentageType | complexity | complexityHighLow | expectedCount
        "351"     | "607"     | "3762"      | "report"  | "99"       | "line"         | null       | "lower"           | 1
        "351"     | "607"     | "3762"      | "report"  | "75"       | "line"         | null       | "higher"          | 0
        "351"     | "607"     | "3762"      | "report"  | "99"       | "method"       | null       | "lower"           | 1
        "351"     | "607"     | "3762"      | "report"  | "75"       | "method"       | null       | "higher"          | 0
        "351"     | "607"     | "3762"      | "report"  | "99"       | "branch"       | null       | "lower"           | 1
        "351"     | "607"     | "3762"      | "report"  | "60"       | "branch"       | null       | "higher"          | 0
        "351"     | "607"     | "3762"      | "group"   | "99"       | "line"         | null       | "lower"           | 20
        "351"     | "607"     | "3762"      | "group"   | "50"       | "line"         | "75"       | "lower"           | 6
        "351"     | "607"     | "3762"      | "group"   | "50"       | "line"         | "75"       | "higher"          | 0
        "351"     | "607"     | "3762"      | "group"   | "99"       | "method"       | null       | "lower"           | 21
        "351"     | "607"     | "3762"      | "group"   | "75"       | "method"       | "25"       | "lower"           | 2
        "351"     | "607"     | "3762"      | "group"   | "75"       | "method"       | "25"       | "higher"          | 7
        "351"     | "607"     | "3762"      | "group"   | "99"       | "branch"       | null       | "lower"           | 24
        "351"     | "607"     | "3762"      | "group"   | "75"       | "branch"       | "25"       | "lower"           | 2
        "351"     | "607"     | "3762"      | "group"   | "75"       | "branch"       | "25"       | "higher"          | 17
        "442"     | "732"     | "3736"      | "report"  | "100"      | "line"         | null       | "lower"           | 1
        "442"     | "732"     | "3736"      | "group"   | "100"      | "line"         | null       | "higher"          | 1
    }
}
