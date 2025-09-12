import {chromium, test} from "@playwright/test"

test("Open Browser", async ({page}) => {
    
    await page.goto("https://www.google.com/")
    await page.getByLabel('Google apps').click();
    
    console.log("First test: ")
})

test ("Open Browser 2", async()=>{

    const browser = await chromium.launch();
    const context = await browser.newContext();
    const page = await context.newPage();

    await page.goto("https://www.google.com/")
    await page.getByLabel('Google apps').click();

    console.log("Second test: ")
}) 
 
