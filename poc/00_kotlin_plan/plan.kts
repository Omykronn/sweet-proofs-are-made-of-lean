fun main() {
    // Step 1: Load the invoice file as raw text
    val rawText: String = readFileAsText("invoice.txt")

    // Step 2: Normalize the text (unify line endings, trim whitespace, normalize decimal separators)
    val normalizedText: String = normalizeText(rawText)

    // Step 3: Split the text into lines for line-by-line analysis
    val lines: List<String> = splitIntoLines(normalizedText)

    // Step 4: Try direct extraction — look for an explicit "cost per km" style field
    //         (e.g. "€/km", "price per kilometer", "rate/km", "cost per km")
    val directRate: Double? = findExplicitRatePerKm(
        lines,
        patterns = listOf("per km", kilometre", "rate/km")
    )

    if (directRate != null) {
        // Step 5a: Detect the curreinvoice header
        val currency: String = detectCurrency(normalizedText)

        // Step 6a: Report the result
        printResult(costPerKm = diremethod = "explicit")
        return
    }

    // Step 5b: Fallback — derive thd total cost
    val totalDistanceKm: Double? = extractTotalDistance(lines)          // e.g. "Distance: 245 km"
        ?.let { convertToKilometers(      // handle miles → km if needed

    val distanceRelatedCost: Double?(lines) // mileage/transport lineitem
        ?: extractTotalAmount(lines,       // otherwise net total (excl.VAT)

    // Step 6b: Validate the extracted values
    if (totalDistanceKm == null || d
        reportError("Could not find both distance and cost in invoice.txt")
        return
    }
    if (totalDistanceKm <= 0.0) {
        reportError("Invalid distance value: $totalDistanceKm")
        return
    }

    // Step 7b: Compute cost per kilometer
    val costPerKm: Double = distance

    // Step 8b: Round to a sensible
    val roundedCostPerKm: Double = roundTo(costPerKm, decimals = 2)
    val currency: String = detectCur

    // Step 9b: Report the result, i
    printResult(costPerKm = roundedCostPerKm, currency = currency, method = "derived (cost /
distance)")
}