package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListingCard {

    /*private final WebElement cardRoot;

    // Simple Indian phone pattern: picks 8-14 digit sequences incl. optional +/0 and separators
    private static final Pattern PHONE_RX = Pattern.compile(
            "(?:\\+?\\d{1,3}[- ]?)?(?:0)?(?:\\d{2,5}[- ]?)?\\d{6,10}"
    );

    public ListingCard(WebElement cardRoot) {
        this.cardRoot = cardRoot;
    }

    public String getName() {
        return cardRoot.findElement(By.xpath(".//a[contains(@href,'/listing/')]")).getText();
    }

    public String getDetails() {
        return cardRoot.getText();
    }

    /**
     * Returns first phone found in the card (normalized) or "N/A" if none present.
     */
    /*public String getPhoneOrNA() {
        String text = getDetails();
        Matcher m = PHONE_RX.matcher(text);
        while (m.find()) {
            String raw = m.group();
            // Normalize by removing spaces, dashes, commas—keep digits and leading '+'
            String normalized = raw.replaceAll("[^\\d+]", "");
            if (normalized.length() >= 8 && normalized.length() <= 14) {
                return normalized;
            }
        }
        return "N/A";
    } */





        private final WebElement root;

        public ListingCard(WebElement root) {
            this.root = root;
        }

        // --- Locators (scoped to a single card root) ---
        private By nameLink() {
            return By.xpath(".//h2[contains(@class,'resultTitle')]/a");
        }

        private By subTitle() {
            return By.xpath(".//span[contains(@class,'resultSubTitle')]");
        }

        private By phoneLinks() {
            return By.xpath(".//div[contains(@class,'cardElement')]//a[starts-with(@href,'tel:')]");
        }

        private By addressBlock() {
            return By.xpath(".//div[contains(@class,'cardElement')][.//i[contains(@class,'glyphicon-map-marker')]]");
        }

        private By servicesBlock() {
            return By.xpath(".//div[contains(@class,'cardElement')][.//i[contains(@class,'glyphicon-check')]]");
        }

        private By recoPercentSpan() {
            return By.xpath(".//span[contains(@class,'avg-reco')]");
        }

        // --- Field getters ---

        public String getName() {
            return root.findElement(nameLink()).getText().trim();
        }

        public String getListingUrl() {
            return root.findElement(nameLink()).getAttribute("href");
        }

        public String getCategory() {
            String t = root.findElement(subTitle()).getText();
            return t == null ? "N/A" : t.trim();
        }

        /** Returns all phone numbers present, or a single element ["N/A"] if none found. */
        public List<String> getPhonesOrNA() {
            List<WebElement> links = root.findElements(phoneLinks());
            if (links.isEmpty()) {
                List<String> na = new ArrayList<>();
                na.add("N/A");
                return na;
            }
            List<String> phones = new ArrayList<>();
            for (WebElement a : links) {
                // visible text usually shows the number in AskLaila cards
                String txt = a.getText().trim();
                phones.add(txt.isEmpty() ? a.getAttribute("href").replace("tel:", "") : txt);
            }
            return phones;
        }

        public String getAddressOrNA() {
            List<WebElement> blocks = root.findElements(addressBlock());
            if (blocks.isEmpty()) return "N/A";
            // icon + text → we just want the text; trim extra whitespace/newlines
            return blocks.get(0).getText().replace("\r", " ").replace("\n", " ").replaceAll("\\s{2,}", " ").trim();
        }

        public String getServicesOrNA() {
            List<WebElement> blocks = root.findElements(servicesBlock());
            if (blocks.isEmpty()) return "N/A";
            return blocks.get(0).getText().trim();
        }

        public String getRecommendationPercentOrNA() {
            List<WebElement> spans = root.findElements(recoPercentSpan());
            if (spans.isEmpty()) return "N/A";
            return spans.get(0).getText().trim(); // e.g., "100%"
        }

        /** Handy for debugging: returns all visible text in the card. */
        public String getRawText() {
            return root.getText();
        }
    }
