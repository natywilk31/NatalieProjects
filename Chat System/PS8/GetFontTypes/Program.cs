using System.Drawing;
using System.Drawing.Text;

namespace GetFontTypes
{
    internal class Program
    {
        static void Main(string[] args)
        {
            FontFamily fontFamily = new FontFamily("Arial");
            Font font = new Font(
               fontFamily,
               8,
               FontStyle.Regular,
               GraphicsUnit.Point);
            RectangleF rectF = new RectangleF(10, 10, 500, 500);
            SolidBrush solidBrush = new SolidBrush(Color.Black);

            string familyName;
            string familyList = "";
            FontFamily[] fontFamilies;

            InstalledFontCollection installedFontCollection = new InstalledFontCollection();

            // Get the array of FontFamily objects.
            fontFamilies = installedFontCollection.Families;

            // The loop below creates a large string that is a comma-separated
            // list of all font family names.

            int count = fontFamilies.Length;
            for (int j = 0; j < count; ++j)
            {
                familyName = fontFamilies[j].Name;
                familyList = familyList + familyName;
                familyList = familyList + ",  ";
            }

            Console.WriteLine(familyList);
            // Draw the large string (list of all families) in a rectangle.
   //         e.Graphics.DrawString(familyList, font, solidBrush, rectF);
        }
    }
}