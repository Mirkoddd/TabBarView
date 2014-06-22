# __TabBarView__

---

An Android Library to help you create actionbar tabs like __"Capitaine train"__ app by Cyril Mottier

![alt tag](https://raw.github.com/Mirkoddd/TabBarView/master/extras/icon.png)

---

### Implementation:

Declare __TabBarView__ and set a ViewPager

```java

		LayoutInflater inflator =
				(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = inflator.inflate(R.layout.custom_ab, null);
		tabBarView = (TabBarView) v.findViewById(R.id.tab_bar);

		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(v);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		tabBarView.setViewPager(mViewPager);


```

Example using a simple pager adapter

```java

	public class SectionsPagerAdapter extends FragmentPagerAdapter implements IconTabProvider{

		private int[] tab_icons={R.drawable.ic_tab1,
				R.drawable.ic_tab2,
				R.drawable.ic_tab3,
		};


		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return tab_icons.length;
		}

		@Override
		public int getPageIconResId(int position) {
			return tab_icons[position];
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

```
---

##Some ScreenShot

![alt tag](https://raw.github.com/Mirkoddd/TabBarView/master/extras/i1.png)
![alt tag](https://raw.github.com/Mirkoddd/TabBarView/master/extras/i2.png)
![alt tag](https://raw.github.com/Mirkoddd/TabBarView/master/extras/i3.png)


####License


 	 Copyright 2014 Mirko Dimartino
 	
 	 Licensed under the Apache License, Version 2.0 (the "License");
 	 you may not use this file except in compliance with the License.
 	 You may obtain a copy of the License at
 	
 	     http://www.apache.org/licenses/LICENSE-2.0
 	
 	 Unless required by applicable law or agreed to in writing, software
	 distributed under the License is distributed on an "AS IS" BASIS,
 	 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 	 See the License for the specific language governing permissions and
 	 limitations under the License.
 	
####Developed by

[Mirko Dimartino](https://www.google.com/+MirkoDimartino_Mirkoddd)
