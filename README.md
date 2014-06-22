# __TabBarView__

---

An Android Library to help you create actionbar tabs like __"Capitaine train"__ app by Cyril Mottier

![alt tag](https://raw.github.com/Mirkoddd/TabBarView/master/extras/icon.png)

---

### Implementation:

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
