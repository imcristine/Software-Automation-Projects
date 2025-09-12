import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:86/')

WebUI.setText(findTestObject('Object Repository/Page_Workflow/input_User ID_txtActiveDirectory'), 'hrc')

WebUI.sendKeys(findTestObject('Object Repository/Page_Workflow/input_User ID_txtActiveDirectory'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Page_CONFI1_LIVE - Workflow/td_Login                                   _1678c1'))

WebUI.selectOptionByValue(findTestObject('Object Repository/Page_CONFI1_LIVE - Workflow/select_CONFI1_LIVEDASHHRCNonConfiPUFFI_WEEKLY_LIVE'), 
    'Cth+jGT9FAjCv46+HUHFGGcPbmoO0Frw+2MxOVl+ceHoMBaYGMEOs07/1xU1iZG0YJheEqMqlM6UxpnSQxGsOVdojS88GRFATt7idA2EkXFm4sWvrTbsvrUzvyGYlcbrvNEfSIaRGWlQK87qskxiV4kuU3TUjxHokVY8jO1z4jQauzhiS/7evefnpa2fmTxIt', 
    true)

WebUI.setEncryptedText(findTestObject('Object Repository/Page_DASHHRCNonConfi - Workflow/input_Password_txtPassword'), 'EMusJp7E+6f603g58TS8pg==')

WebUI.sendKeys(findTestObject('Object Repository/Page_DASHHRCNonConfi - Workflow/input_Password_txtPassword'), Keys.chord(
        Keys.ENTER))

