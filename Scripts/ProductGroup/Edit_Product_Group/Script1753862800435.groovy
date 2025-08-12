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

WebUI.setText(findTestObject('Object Repository/EPC/Property_Management/Product_Group/input__name_edit'), name.toString())

WebUI.setText(findTestObject('Object Repository/EPC/Property_Management/Product_Group/input__code_edit'), code.toString())

WebUI.setText(findTestObject('Object Repository/EPC/Property_Management/Product_Group/textarea_Description_edit'), description.toString())

TestObject startDateField = findTestObject('Object Repository/EPC/Property_Management/Product_Group/input_ValidFor_startDate')
TestObject endDateField = findTestObject('Object Repository/EPC/Property_Management/Product_Group/input_ValidFor_endDate')

// Xóa trước rồi gán giá trị ngày mới cho startDate
WebUI.executeJavaScript(
	"arguments[0].value=''; arguments[0].dispatchEvent(new Event('input'));" +
	"arguments[0].value='" + startDate.toString() + "'; arguments[0].dispatchEvent(new Event('change'))",
	Arrays.asList(WebUI.findWebElement(startDateField))
)

// Tương tự cho endDate
WebUI.executeJavaScript(
	"arguments[0].value=''; arguments[0].dispatchEvent(new Event('input'));" +
	"arguments[0].value='" + endDate.toString() + "'; arguments[0].dispatchEvent(new Event('change'))",
	Arrays.asList(WebUI.findWebElement(endDateField))
)

if (!status.toString().blank) {
	WebUI.click(findTestObject("Object Repository/EPC/Property_Management/Product_Group/div_Status_combobox"))
	String valueToSelect = status.toString()
	TestObject option = findTestObject('Object Repository/EPC/Property_Management/Product_Group/div_Status_option', [('option'): valueToSelect])
	WebUI.waitForElementPresent(option, 5)
	WebUI.click(option)
}

WebUI.setText(findTestObject('Object Repository/EPC/Property_Management/Product_Group/input_Version'), version.toString())

