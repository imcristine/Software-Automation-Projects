import {test} from "@playwright/test"

test ("Locator Method", async ({page}) => {
    await page.goto("http://localhost:94/")
    //await page.getByRole('textbox', { name: 'ID Number' }).fill("HRC");
    await page.locator("//*[@name='UserName']").fill("HRC");            // name
    await page.locator("input#txtPassword").fill("systemadmin");        //id
    await page.locator("button.btn").click();                           //Class

}) 

