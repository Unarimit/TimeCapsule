using Microsoft.VisualStudio.TestTools.UnitTesting;
using TimeCapsule.Infrastructure.Utils;
using System;
using System.Collections.Generic;
using System.Text;

namespace TimeCapsule.Infrastructure.Utils.Tests
{
    [TestClass()]
    public class TimeHelperTests
    {
        [TestMethod()]
        public void ConvertFromUnixTimestampTest()
        {
            DateTime date = TimeHelper.ConvertFromUnixTimestamp(1615507200);
            DateTime target = new DateTime(2021, 3, 12, 0, 0, 0);
            Assert.AreEqual(date, target);
        }

        [TestMethod()]
        public void ConvertToUnixTimestampTest()
        {
            long date = TimeHelper.ConvertToUnixTimestamp(new DateTime(2021, 3, 12, 0, 0, 0));
            long target = 1615507200;
            Assert.AreEqual(date, target);
        }
    }
}